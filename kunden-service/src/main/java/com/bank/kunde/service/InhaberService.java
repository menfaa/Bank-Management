package com.bank.kunde.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.kunde.domain.Inhaber;
import com.bank.kunde.execptions.NoInhaberFoundException;
import com.bank.kunde.facory.InhaberFactory;
import com.bank.kunde.repository.InhaberRepository;
import com.bank.common.InhaberID; //InhaberID
import com.bank.common.events.InhaberDeletedEvent;

// Service für Geschäftslogik rund um Girokonto
@Service
public class InhaberService {

    private final InhaberRepository repository;
    private final InhaberFactory factory;
    private final KafkaTemplate<String, InhaberDeletedEvent> kafkaTemplate;

    public InhaberService(InhaberRepository repository, InhaberFactory factory,
            KafkaTemplate<String, InhaberDeletedEvent> kafkaTemplate) {
        this.repository = repository;
        this.factory = factory;
        this.kafkaTemplate = kafkaTemplate;
    }

    // Erzeugt und speichert ein neues Girokonto
    public Inhaber createInhaber(String inhaberIdValue, String name) {
        Inhaber inhaber = factory.create(inhaberIdValue, name);
        repository.save(inhaber);
        return inhaber;
    }

    // Sucht ein Girokonto nach IBAN
    public Iterable<Inhaber> findAllInhaber() {
        return repository.findAll();
    }

    // Sucht ein Girokonto nach IBAN
    public Inhaber findByInhaberId(String inhaberIdValue) {
        InhaberID inhaberId = new InhaberID(inhaberIdValue);
        return repository.findById(inhaberId).orElseThrow(() -> new NoInhaberFoundException(inhaberId));
    }

    // Löscht ein Girokonto nach IBAN
    @Transactional
    public void deleteByInhaberId(String inhaberIdValue) {
        InhaberID inhaberId = new InhaberID(inhaberIdValue);
        repository.deleteById(inhaberId);

        try {
            // .get() erzwingt das Warten auf das Ergebnis und zeigt Fehler sofort!
            kafkaTemplate.send("inhaber-deleted", new InhaberDeletedEvent(inhaberId)).get();
            System.out.println(">>> KAFKA: Event erfolgreich an Broker übergeben");
        } catch (Exception e) {
            System.err.println(">>> KAFKA FEHLER: " + e.getMessage());
            // Wichtig: Exception werfen, damit DB-Delete auch zurückgerollt wird bei
            // Kafka-Fehler
            throw new RuntimeException("Kafka Versand fehlgeschlagen", e);
        }
    }

    // Save Girokonto
    public void save(Inhaber inhaber) {
        repository.save(inhaber);
    }

}