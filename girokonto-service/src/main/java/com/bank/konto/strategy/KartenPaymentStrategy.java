package com.bank.konto.strategy;

import com.bank.common.Betrag;
import com.bank.common.events.PaymentRequestedEvent;
import com.bank.konto.domain.Girokonto;
import org.springframework.kafka.core.KafkaTemplate; // Spring: Ermöglicht das Senden von Nachrichten an Kafka
import java.time.LocalDate;

/**
 * Implementiert die PaymentStrategy für Kartenzahlungen.
 * Sendet ein PaymentRequestedEvent über Kafka, um eine Kartenzahlung auszulösen.
 */
public class KartenPaymentStrategy implements PaymentStrategy {
    private final String kartennummer; // Die Kartennummer für die Zahlung
    private final String haendler; // Der Händler, bei dem gezahlt wird
    private final KafkaTemplate<String, PaymentRequestedEvent> kafkaTemplate; // Kafka-Template zum Senden von Events

    /**
     * Konstruktor für die KartenPaymentStrategy.
     * @param kartennummer Die Kartennummer
     * @param haendler Der Händler
     * @param kafkaTemplate Kafka-Template zum Senden von Events
     */
    public KartenPaymentStrategy(String kartennummer, String haendler,
            KafkaTemplate<String, PaymentRequestedEvent> kafkaTemplate) {
        this.kartennummer = kartennummer;
        this.haendler = haendler;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Führt die Zahlung aus, indem ein PaymentRequestedEvent an Kafka gesendet wird.
     */
    @Override
    public void executePayment(Girokonto girokonto, Betrag betrag, String verwendungszweck) {
        PaymentRequestedEvent event = new PaymentRequestedEvent(
                girokonto.getIban().getValue(),
                betrag,
                verwendungszweck,
                kartennummer,
                haendler,
                LocalDate.now());
        kafkaTemplate.send("payment-requested", event); // Sendet das Event an das Kafka-Topic "payment-requested"
    }

}