package com.bank.kunde.config;


import com.bank.common.events.InhaberDeletedEvent; // Importiert das Event InhaberDeletedEvent
import com.bank.common.events.PaymentCompletedEvent; // Importiert das Event PaymentCompletedEvent

import java.util.HashMap; // Importiert HashMap für die Alias-Map
import java.util.Map; // Importiert Map-Interface

import org.springframework.context.annotation.Bean; // Importiert die Bean-Annotation für Spring
import org.springframework.context.annotation.Configuration; // Importiert die Configuration-Annotation für Spring
import org.springframework.kafka.support.DefaultKafkaHeaderMapper; // Importiert den DefaultKafkaHeaderMapper
import org.springframework.kafka.support.converter.JsonMessageConverter; // Importiert den JsonMessageConverter für Kafka
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper; // Importiert den DefaultJackson2JavaTypeMapper
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper; // Importiert das Jackson2JavaTypeMapper-Interface

@Configuration // Markiert die Klasse als Konfigurationsklasse für Spring
public class KafkaConfig {

    @Bean // Markiert die Methode als Bean, die von Spring verwaltet wird
    public JsonMessageConverter jsonConverter(DefaultJackson2JavaTypeMapper typeMapper) {
        JsonMessageConverter converter = new JsonMessageConverter(); // Erstellt einen neuen JSON-MessageConverter
        converter.setTypeMapper(typeMapper); // Verknüpft den TypeMapper mit dem Converter (für Alias-Mappings)
        return converter;
    }

    @Bean // Erstellt eine Bean für den HeaderMapper (wird für Kafka-Header benötigt)
    public DefaultKafkaHeaderMapper headerMapper() {
        return new DefaultKafkaHeaderMapper();
    }

    // Diese Map löst dein Problem mit den falschen Klassen-Konvertierungen
    @Bean // Erstellt eine Bean für den TypeMapper
    public DefaultJackson2JavaTypeMapper typeMapper() {
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper(); // Erstellt einen neuen TypeMapper
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID); // Setzt die Priorität auf TYPE_ID
        typeMapper.addTrustedPackages("com.bank.common.events"); // Erlaubt nur Events aus diesem Package

        Map<String, Class<?>> mappings = new HashMap<>(); // Erstellt eine Map für Alias-Namen und Klassen
        // Hier definierst du die "Alias"-Namen für deine Events
        mappings.put("inhaber_deleted", InhaberDeletedEvent.class); // Alias für InhaberDeletedEvent
        mappings.put("payment_completed", PaymentCompletedEvent.class); // Alias für PaymentCompletedEvent

        typeMapper.setIdClassMapping(mappings); // Setzt die Alias-Map im TypeMapper
        return typeMapper;
    }
}