package com.bank.zahlung.service;

import com.bank.common.events.PaymentCompletedEvent;
import com.bank.common.events.PaymentRequestedEvent;
import com.bank.zahlung.domain.Kartenzahlung;
import com.bank.zahlung.domain.SEPAUeberweisung;
import com.bank.zahlung.domain.Zahlung;
import com.bank.zahlung.repository.ZahlungsRepository;

import org.springframework.kafka.core.KafkaTemplate; // Für das Senden von Events an Kafka
import org.springframework.stereotype.Service; // Markiert die Klasse als Service-Komponente

import java.util.Optional;

/**
 * Service für die Geschäftslogik rund um Zahlungen.
 * Kapselt alle Operationen wie Erstellen, Suchen, Speichern und das Senden von Events.
 */
@Service // Spring: Markiert diese Klasse als Service, sodass sie von Spring verwaltet wird
public class ZahlungsService {

    private final ZahlungsRepository repo; // Repository für Datenbankzugriffe
    private final KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate; // Kafka-Template zum Senden von Events

    /**
     * Konstruktor für Dependency Injection von Repository und KafkaTemplate.
     */
    public ZahlungsService(ZahlungsRepository repo, KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate) {
        this.repo = repo;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Gibt alle Zahlungen zurück.
     */
    public Iterable<Zahlung> findAllZahlungen() {
        return repo.findAll();
    }

    /**
     * Speichert eine Zahlung.
     */
    public void save(Zahlung zahlung) {
        repo.save(zahlung);
    }

    /**
     * Sucht eine Zahlung nach ID.
     * @throws RuntimeException wenn keine Zahlung gefunden wird
     */
    public Zahlung findById(String id) {
        Optional<Zahlung> zahlung = repo.findById(id);
        return zahlung.orElseThrow(() -> new RuntimeException("Zahlung nicht gefunden: " + id));
    }

    /**
     * Speichert eine Zahlung und sendet ein PaymentCompletedEvent an Kafka.
     */
    public void saveZahlung(Zahlung zahlung) {
        repo.save(zahlung);

        String girokontoIban = null;
        if (zahlung instanceof SEPAUeberweisung sepa) {
            girokontoIban = sepa.getAuftraggeberIban();
        } else if (zahlung instanceof Kartenzahlung karte) {
            girokontoIban = karte.getGirokontoIban();
        }

        PaymentCompletedEvent event = new PaymentCompletedEvent(
                girokontoIban,
                zahlung.getId(),
                zahlung.getClass().getSimpleName(),
                zahlung.getVerwendungszweck(),
                zahlung.getBetrag().wert());
        kafkaTemplate.send("payment-completed", event);
    }

    /**
     * Erstellt und speichert eine Kartenzahlung aus einem PaymentRequestedEvent.
     */
    public void createKartenzahlung(PaymentRequestedEvent event) {
        Kartenzahlung kartenzahlung = new Kartenzahlung(
                event.getGirokontoIban(),
                event.getDatum(),
                event.getBetrag(),
                event.getVerwendungszweck(),
                event.getKartennummer(),
                event.getHaendler());
        saveZahlung(kartenzahlung);
    }

    /**
     * Erstellt und speichert eine SEPA-Überweisung aus einem PaymentRequestedEvent.
     */
    public void createSepaZahlung(PaymentRequestedEvent event) {
        SEPAUeberweisung sepa = new SEPAUeberweisung(
                event.getGirokontoIban(),
                event.getEmpfaengerIban(),
                event.getEmpfaengerName(),
                event.getBetrag(),
                event.getDatum(),
                event.getVerwendungszweck());
        saveZahlung(sepa);
    }

}