package com.bank.konto.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.konto.domain.Girokonto;
import com.bank.konto.domain.Kontoauszug;
import com.bank.konto.execptions.NoGirokontoFoundException;
import com.bank.konto.facory.GirokontoFactory;
import com.bank.konto.repository.GirokontoRepository;
import com.bank.common.Betrag;
import com.bank.common.IBAN;
import com.bank.common.InhaberID;
import com.bank.common.events.PaymentCompletedEvent;

// Service für Geschäftslogik rund um Girokonto
@Service
public class GirokontoService {

    private final GirokontoRepository repository;
    private final GirokontoFactory factory;

    public GirokontoService(GirokontoRepository repository, GirokontoFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    // Erzeugt und speichert ein neues Girokonto
    public Girokonto createGirokonto(String ibanValue, InhaberID inhaberId) {
        Girokonto girokonto = factory.create(ibanValue, inhaberId);
        repository.save(girokonto);
        return girokonto;
    }

    // Sucht ein Girokonto nach IBAN
    public Iterable<Girokonto> findAllGirokontos() {
        return repository.findAll();
    }

    // Sucht ein Girokonto nach IBAN
    public Girokonto findByIban(String ibanValue) {
        IBAN iban = new IBAN(ibanValue);
        return repository.findByIban(iban).orElseThrow(() -> new NoGirokontoFoundException(iban));
    }

    // Sucht ein Girokonto nach IBAN
    public Optional<Girokonto> findByIbanWithKontoauszuege(String ibanValue) {
        return repository.findByIbanWithKontoauszuege(ibanValue);
    }

    // Löscht ein Girokonto nach IBAN
    @Transactional
    public void deleteByIban(String ibanValue) {
        IBAN iban = new IBAN(ibanValue);
        repository.deleteByIban(iban);
    }

    // Save Girokonto
    public void save(Girokonto konto) {
        repository.save(konto);
    }

    @Transactional
    public void deleteByInhaberId(String inhaberIdValue) {
        InhaberID inhaberId = new InhaberID(inhaberIdValue);
        repository.deleteAllByInhaberId(inhaberId);
    }

    public Iterable<Girokonto> getAllGirokontos() {
        return repository.findAll();
    }

    @Transactional
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