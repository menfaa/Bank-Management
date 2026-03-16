package com.bank.konto.strategy;

import com.bank.common.events.PaymentRequestedEvent;
import com.bank.konto.domain.Girokonto;
import com.bank.common.Betrag;
import org.springframework.kafka.core.KafkaTemplate;
import java.time.LocalDate;

public class SepaPaymentStrategy implements PaymentStrategy {

    private final String empfaengerIban;
    private final String empfaengerName;
    private final KafkaTemplate<String, PaymentRequestedEvent> kafkaTemplate;

    public SepaPaymentStrategy(String empfaengerIban, String empfaengerName,
            KafkaTemplate<String, PaymentRequestedEvent> kafkaTemplate) {
        this.empfaengerIban = empfaengerIban;
        this.empfaengerName = empfaengerName;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void executePayment(Girokonto girokonto, Betrag betrag, String verwendungszweck) {
        PaymentRequestedEvent event = new PaymentRequestedEvent(
                girokonto.getIban().getValue(),
                betrag,
                verwendungszweck,
                LocalDate.now(),
                empfaengerIban,
                empfaengerName);
        kafkaTemplate.send("payment-requested", event);
    }

}