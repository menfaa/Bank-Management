package com.bank.zahlung.service;

import com.bank.common.events.PaymentCompletedEvent;
import com.bank.common.events.PaymentRequestedEvent;
import com.bank.zahlung.domain.Kartenzahlung;
import com.bank.zahlung.domain.SEPAUeberweisung;
import com.bank.zahlung.domain.Zahlung;
import com.bank.zahlung.repository.ZahlungsRepository;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ZahlungsService {

    private final ZahlungsRepository repo;
    private final KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate;

    public ZahlungsService(ZahlungsRepository repo, KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate) {
        this.repo = repo;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Iterable<Zahlung> findAllZahlungen() {
        return repo.findAll();
    }

    public void save(Zahlung zahlung) {
        repo.save(zahlung);
    }

    public Zahlung findById(String id) {
        Optional<Zahlung> zahlung = repo.findById(id);
        return zahlung.orElseThrow(() -> new RuntimeException("Zahlung nicht gefunden: " + id));
    }

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