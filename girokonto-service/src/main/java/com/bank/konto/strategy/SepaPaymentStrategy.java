package com.bank.konto.strategy;

import com.bank.common.events.PaymentRequestedEvent;
import com.bank.konto.domain.Girokonto;
import com.bank.common.Betrag;
import org.springframework.kafka.core.KafkaTemplate; // Spring: Ermöglicht das Senden von Nachrichten an Kafka
import java.time.LocalDate;

/**
 * Implementiert die PaymentStrategy für SEPA-Zahlungen.
 * Sendet ein PaymentRequestedEvent über Kafka, um eine SEPA-Zahlung auszulösen.
 */
public class SepaPaymentStrategy implements PaymentStrategy {

    private final String empfaengerIban; // IBAN des Empfängers
    private final String empfaengerName; // Name des Empfängers
    private final KafkaTemplate<String, PaymentRequestedEvent> kafkaTemplate; // Kafka-Template zum Senden von Events

    /**
     * Konstruktor für die SepaPaymentStrategy.
     * @param empfaengerIban IBAN des Empfängers
     * @param empfaengerName Name des Empfängers
     * @param kafkaTemplate Kafka-Template zum Senden von Events
     */
    public SepaPaymentStrategy(String empfaengerIban, String empfaengerName,
            KafkaTemplate<String, PaymentRequestedEvent> kafkaTemplate) {
        this.empfaengerIban = empfaengerIban;
        this.empfaengerName = empfaengerName;
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
                LocalDate.now(),
                empfaengerIban,
                empfaengerName);
        kafkaTemplate.send("payment-requested", event); // Sendet das Event an das Kafka-Topic "payment-requested"
    }

}