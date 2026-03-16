package com.bank.konto.service;

import com.bank.common.InhaberID;
import com.bank.common.events.InhaberDeletedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class GirokontoKafkaListener {

    private final GirokontoService girokontoService;

    public GirokontoKafkaListener(GirokontoService girokontoService) {
        this.girokontoService = girokontoService;
    }

    @KafkaListener(topics = "inhaber-deleted", groupId = "girokonto-service")
    public void handleInhaberDeleted(InhaberDeletedEvent event) {
        InhaberID inhaberId = event.getInhaberId();
        girokontoService.deleteByInhaberId(inhaberId.getValue());
        System.out.println("Alle Girokonten für Inhaber " + inhaberId + " wurden gelöscht (Kafka).");
    }
}