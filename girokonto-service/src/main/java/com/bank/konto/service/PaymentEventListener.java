package com.bank.konto.service;

import com.bank.common.events.PaymentCompletedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventListener {

    private final GirokontoService girokontoService;

    public PaymentEventListener(GirokontoService girokontoService) {
        this.girokontoService = girokontoService;
    }

    @KafkaListener(topics = "payment-completed", groupId = "girokonto-service")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        // Beispiel: Kontoauszug hinzufügen
        girokontoService.addKontoauszugNachZahlung(event);
        System.out.println("Zahlung verarbeitet für IBAN: " + event.getGirokontoIban());
    }
}