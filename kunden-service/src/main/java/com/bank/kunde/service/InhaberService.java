package com.bank.kunde.service;

import org.springframework.kafka.core.KafkaTemplate; // Für das Senden von Events an Kafka
import org.springframework.stereotype.Service; // Markiert die Klasse als Service-Komponente
import org.springframework.transaction.annotation.Transactional; // Für Transaktionsmanagement

import com.bank.kunde.domain.Inhaber;
import com.bank.kunde.execptions.NoInhaberFoundException;
import com.bank.kunde.facory.InhaberFactory;
import com.bank.kunde.repository.InhaberRepository;
import com.bank.common.InhaberID; // Value Object für InhaberID
import com.bank.common.events.InhaberDeletedEvent;

/**
 * Service für die Geschäftslogik rund um Inhaber.
 * Kapselt alle Operationen wie Erstellen, Suchen, Löschen und das Senden von Events.
 */
@Service // Spring: Markiert diese Klasse als Service, sodass sie von Spring verwaltet wird
public class InhaberService {

    private final InhaberRepository repository; // Repository für Datenbankzugriffe
    private final InhaberFactory factory; // Factory zur Erstellung von Inhabern
    private final KafkaTemplate<String, InhaberDeletedEvent> kafkaTemplate; // Kafka-Template zum Senden von Events

    /**
     * Konstruktor für Dependency Injection von Repository, Factory und KafkaTemplate.
     */
    public InhaberService(InhaberRepository repository, InhaberFactory factory,
            KafkaTemplate<String, InhaberDeletedEvent> kafkaTemplate) {
        this.repository = repository;
        this.factory = factory;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Erzeugt und speichert einen neuen Inhaber.
     */
    public Inhaber createInhaber(String inhaberIdValue, String name) {
        Inhaber inhaber = factory.create(inhaberIdValue, name);
        repository.save(inhaber);
        return inhaber;
    }

    /**
     * Gibt alle Inhaber zurück.
     */
    public Iterable<Inhaber> findAllInhaber() {
        return repository.findAll();
    }

    /**
     * Sucht einen Inhaber nach InhaberID.
     * @throws NoInhaberFoundException wenn kein Inhaber gefunden wird
     */
    public Inhaber findByInhaberId(String inhaberIdValue) {
        InhaberID inhaberId = new InhaberID(inhaberIdValue);
        return repository.findById(inhaberId).orElseThrow(() -> new NoInhaberFoundException(inhaberId));
    }

    /**
     * Löscht einen Inhaber nach InhaberID und sendet ein Event an Kafka.
     * Die Methode ist transaktional: Bei Fehlern wird die Löschung zurückgerollt.
     */
    @Transactional // Spring: Führt die Methode in einer Transaktion aus
    public void deleteByInhaberId(String inhaberIdValue) {
        InhaberID inhaberId = new InhaberID(inhaberIdValue);
        repository.deleteById(inhaberId);

        try {
            // .get() erzwingt das Warten auf das Ergebnis und zeigt Fehler sofort!
            kafkaTemplate.send("inhaber-deleted", new InhaberDeletedEvent(inhaberId)).get();
            System.out.println(">>> KAFKA: Event erfolgreich an Broker übergeben");
        } catch (Exception e) {
            System.err.println(">>> KAFKA FEHLER: " + e.getMessage());
            // Wichtig: Exception werfen, damit DB-Delete auch zurückgerollt wird bei Kafka-Fehler
            throw new RuntimeException("Kafka Versand fehlgeschlagen", e);
        }
    }

    /**
     * Speichert einen Inhaber.
     */
    public void save(Inhaber inhaber) {
        repository.save(inhaber);
    }

}