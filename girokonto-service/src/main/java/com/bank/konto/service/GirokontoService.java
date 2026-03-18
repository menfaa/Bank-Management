package com.bank.konto.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service; // Spring: Markiert die Klasse als Service-Komponente
import org.springframework.transaction.annotation.Transactional; // Spring: Für Transaktionsmanagement

import com.bank.konto.domain.Girokonto;
import com.bank.konto.domain.Kontoauszug;
import com.bank.konto.execptions.NoGirokontoFoundException;
import com.bank.konto.facory.GirokontoFactory;
import com.bank.konto.repository.GirokontoRepository;
import com.bank.common.Betrag;
import com.bank.common.IBAN;
import com.bank.common.InhaberID;
import com.bank.common.events.PaymentCompletedEvent;

/**
 * Service für die Geschäftslogik rund um Girokonten.
 * Kapselt alle Operationen wie Erstellen, Suchen, Löschen und das Hinzufügen von Kontoauszügen.
 */
@Service // Spring: Markiert diese Klasse als Service, sodass sie von Spring verwaltet wird
public class GirokontoService {

    private final GirokontoRepository repository; // Repository für Datenbankzugriffe
    private final GirokontoFactory factory; // Factory zur Erstellung von Girokonten

    /**
     * Konstruktor für Dependency Injection von Repository und Factory.
     */
    public GirokontoService(GirokontoRepository repository, GirokontoFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    /**
     * Erzeugt und speichert ein neues Girokonto.
     */
    public Girokonto createGirokonto(String ibanValue, InhaberID inhaberId) {
        Girokonto girokonto = factory.create(ibanValue, inhaberId);
        repository.save(girokonto);
        return girokonto;
    }

    /**
     * Gibt alle Girokonten zurück.
     */
    public Iterable<Girokonto> findAllGirokontos() {
        return repository.findAll();
    }

    /**
     * Sucht ein Girokonto nach IBAN.
     * @throws NoGirokontoFoundException wenn kein Konto gefunden wird
     */
    public Girokonto findByIban(String ibanValue) {
        IBAN iban = new IBAN(ibanValue);
        return repository.findByIban(iban).orElseThrow(() -> new NoGirokontoFoundException(iban));
    }

    /**
     * Sucht ein Girokonto nach IBAN und lädt die Kontoauszüge mit.
     */
    public Optional<Girokonto> findByIbanWithKontoauszuege(String ibanValue) {
        return repository.findByIbanWithKontoauszuege(ibanValue);
    }

    /**
     * Löscht ein Girokonto nach IBAN.
     */
    @Transactional // Spring: Führt die Methode in einer Transaktion aus
    public void deleteByIban(String ibanValue) {
        IBAN iban = new IBAN(ibanValue);
        repository.deleteByIban(iban);
    }

    /**
     * Speichert ein Girokonto.
     */
    public void save(Girokonto konto) {
        repository.save(konto);
    }

    /**
     * Löscht alle Girokonten eines bestimmten Inhabers.
     */
    @Transactional // Spring: Führt die Methode in einer Transaktion aus
    public void deleteByInhaberId(String inhaberIdValue) {
        InhaberID inhaberId = new InhaberID(inhaberIdValue);
        repository.deleteAllByInhaberId(inhaberId);
    }

    /**
     * Gibt alle Girokonten zurück (alternative Methode).
     */
    public Iterable<Girokonto> getAllGirokontos() {
        return repository.findAll();
    }

    /**
     * Fügt nach einer erfolgreichen Zahlung einen Kontoauszug hinzu.
     */
    @Transactional // Spring: Führt die Methode in einer Transaktion aus
    public void addKontoauszugNachZahlung(PaymentCompletedEvent event) {
        IBAN iban = new IBAN(event.getGirokontoIban());
        Optional<Girokonto> konto = repository.findByIban(iban);
        if (konto.isPresent()) {
            Girokonto girokonto = konto.get();
            // Erzeuge einen neuen Kontoauszug (oder Buchung)
            Kontoauszug auszug = new Kontoauszug(
                    new IBAN(event.getGirokontoIban()),
                    LocalDate.now(),
                    new Betrag(event.getBetrag()),
                    event.getVerwendungszweck());
            girokonto.addKontoauszug(auszug);
            repository.save(girokonto);
        }
    }

}