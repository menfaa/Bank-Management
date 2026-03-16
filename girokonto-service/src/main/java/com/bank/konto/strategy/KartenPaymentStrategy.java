package com.bank.konto.strategy;

import com.bank.common.Betrag;
import com.bank.common.events.PaymentRequestedEvent;
import com.bank.konto.domain.Girokonto;
import org.springframework.kafka.core.KafkaTemplate;
import java.time.LocalDate;

public class KartenPaymentStrategy implements PaymentStrategy {
    private final String kartennummer;
    private final String haendler;
    private final KafkaTemplate<String, PaymentRequestedEvent> kafkaTemplate;

    public KartenPaymentStrategy(String kartennummer, String haendler,
            KafkaTemplate<String, PaymentRequestedEvent> kafkaTemplate) {
        this.kartennummer = kartennummer;
        this.haendler = haendler;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void executePayment(Girokonto girokonto, Betrag betrag, String verwendungszweck) {
        PaymentRequestedEvent event = new PaymentRequestedEvent(
                girokonto.getIban().getValue(),
                betrag,
                verwendungszweck,
                kartennummer,
                haendler,
                LocalDate.now());
        kafkaTemplate.send("payment-requested", event);
    }

}