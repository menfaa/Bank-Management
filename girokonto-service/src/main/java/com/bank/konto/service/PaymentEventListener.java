package com.bank.konto.service;

import com.bank.common.events.PaymentCompletedEvent;
import org.springframework.kafka.annotation.KafkaListener; // Spring: Markiert Methoden als Kafka-Listener
import org.springframework.stereotype.Service; // Spring: Markiert die Klasse als Service-Komponente

/**
 * Service, der auf Kafka-Nachrichten zum Abschluss von Zahlungen reagiert.
 * Fügt nach einer erfolgreichen Zahlung einen Kontoauszug hinzu.
 */
@Service // Spring: Markiert diese Klasse als Service, sodass sie von Spring verwaltet wird
public class PaymentEventListener {

    private final GirokontoService girokontoService; // Service für Girokonten

    /**
     * Konstruktor für Dependency Injection des GirokontoService.
     */
    public PaymentEventListener(GirokontoService girokontoService) {
        this.girokontoService = girokontoService;
    }

    /**
     * Wird aufgerufen, wenn eine Nachricht auf dem Topic "payment-completed" empfangen wird.
     * Fügt dem entsprechenden Girokonto einen Kontoauszug hinzu.
     * @param event Das empfangene PaymentCompletedEvent
     */
    @KafkaListener(topics = "payment-completed", groupId = "girokonto-service") // Spring: Methode hört auf Kafka-Topic "payment-completed"
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        // Beispiel: Kontoauszug hinzufügen
        girokontoService.addKontoauszugNachZahlung(event);
        System.out.println("Zahlung verarbeitet für IBAN: " + event.getGirokontoIban());
    }
}