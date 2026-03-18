package com.bank.konto.service;

import com.bank.common.InhaberID;
import com.bank.common.events.InhaberDeletedEvent;
import org.springframework.kafka.annotation.KafkaListener; // Spring: Markiert Methoden als Kafka-Listener
import org.springframework.stereotype.Service; // Spring: Markiert die Klasse als Service-Komponente

/**
 * Service, der auf Kafka-Nachrichten zum Löschen von Inhabern reagiert.
 * Löscht alle Girokonten eines Inhabers, wenn ein InhaberDeletedEvent empfangen wird.
 */
@Service // Spring: Markiert diese Klasse als Service, sodass sie von Spring verwaltet wird
public class GirokontoKafkaListener {

    private final GirokontoService girokontoService; // Service für Girokonten

    /**
     * Konstruktor für Dependency Injection des GirokontoService.
     */
    public GirokontoKafkaListener(GirokontoService girokontoService) {
        this.girokontoService = girokontoService;
    }

    /**
     * Wird aufgerufen, wenn eine Nachricht auf dem Topic "inhaber-deleted" empfangen wird.
     * Löscht alle Girokonten des angegebenen Inhabers.
     * @param event Das empfangene InhaberDeletedEvent
     */
    @KafkaListener(topics = "inhaber-deleted", groupId = "girokonto-service") // Spring: Methode hört auf Kafka-Topic "inhaber-deleted"
    public void handleInhaberDeleted(InhaberDeletedEvent event) {
        InhaberID inhaberId = event.getInhaberId();
        girokontoService.deleteByInhaberId(inhaberId.getValue());
        System.out.println("Alle Girokonten für Inhaber " + inhaberId + " wurden gelöscht (Kafka).");
    }
}