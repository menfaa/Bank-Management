package com.bank.zahlung.service;

import com.bank.common.events.PaymentRequestedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentRequestedListener {

    private final ZahlungsService zahlungsService;

    public PaymentRequestedListener(ZahlungsService zahlungsService) {
        this.zahlungsService = zahlungsService;
    }

    @KafkaListener(topics = "payment-requested", groupId = "zahlungs-service")
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