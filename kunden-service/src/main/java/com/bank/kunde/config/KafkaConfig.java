package com.bank.kunde.config;

import com.bank.common.events.InhaberDeletedEvent;
import com.bank.common.events.PaymentCompletedEvent;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;

@Configuration
public class KafkaConfig {

    @Bean
    public JsonMessageConverter jsonConverter() {
        return new JsonMessageConverter();
    }

    @Bean
    public DefaultKafkaHeaderMapper headerMapper() {
        return new DefaultKafkaHeaderMapper();
    }

    // Diese Map löst dein Problem mit den falschen Klassen-Konvertierungen
    @Bean
    public DefaultJackson2JavaTypeMapper typeMapper() {
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("com.bank.common.events");

        Map<String, Class<?>> mappings = new HashMap<>();
        // Hier definierst du die "Alias"-Namen für deine Events
        mappings.put("inhaber_deleted", InhaberDeletedEvent.class);
        mappings.put("payment_completed", PaymentCompletedEvent.class);

        typeMapper.setIdClassMapping(mappings);
        return typeMapper;
    }
}
