package com.bank.zahlung.service;

import com.bank.common.events.PaymentRequestedEvent;
import org.springframework.kafka.annotation.KafkaListener; // Spring: Markiert Methoden als Kafka-Listener
import org.springframework.stereotype.Service; // Markiert die Klasse als Service-Komponente

/**
 * Service, der auf Kafka-Nachrichten zum Anfordern von Zahlungen reagiert.
 * Erstellt je nach Event-Typ eine Kartenzahlung oder SEPA-Überweisung.
 */
@Service // Spring: Markiert diese Klasse als Service, sodass sie von Spring verwaltet wird
public class PaymentRequestedListener {

    private final ZahlungsService zahlungsService; // Service für Zahlungs-Operationen

    /**
     * Konstruktor für Dependency Injection des ZahlungsService.
     */
    public PaymentRequestedListener(ZahlungsService zahlungsService) {
        this.zahlungsService = zahlungsService;
    }

    /**
     * Wird aufgerufen, wenn eine Nachricht auf dem Topic "payment-requested" empfangen wird.
     * Unterscheidet zwischen Kartenzahlung und SEPA-Überweisung anhand der Event-Daten.
     * @param event Das empfangene PaymentRequestedEvent
     */
    @KafkaListener(topics = "payment-requested", groupId = "zahlungs-service") // Spring: Methode hört auf Kafka-Topic "payment-requested"
    public void handlePaymentRequested(PaymentRequestedEvent event) {
        System.out.println("PaymentRequestedEvent empfangen: " + event);

        if (event.getKartennummer() != null && event.getHaendler() != null) {
            // Kartenzahlung
            zahlungsService.createKartenzahlung(event);
        } else if (event.getEmpfaengerIban() != null && event.getEmpfaengerName() != null) {
            // SEPA-Überweisung
            zahlungsService.createSepaZahlung(event);
        } else {
            System.err.println("Unbekannter oder unvollständiger PaymentRequestedEvent: " + event);
        }
    }

}