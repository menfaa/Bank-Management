package com.bank.zahlung.config;

import com.fasterxml.jackson.databind.Module; // Jackson-Modul-Interface
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Modul für Java 8 Date/Time API Unterstützung
import org.springframework.context.annotation.Bean; // Markiert Methoden als Beans für Spring
import org.springframework.context.annotation.Configuration; // Markiert die Klasse als Konfigurationsklasse

/**
 * Konfiguriert Jackson für die Unterstützung von Java 8 Date/Time-Typen (z.B. LocalDate).
 * Dadurch kann Spring automatisch Datumswerte korrekt serialisieren und deserialisieren.
 */
@Configuration // Markiert die Klasse als Spring-Konfigurationsklasse
public class JacksonConfig {
    /**
     * Registriert das JavaTimeModule als Bean, damit Jackson LocalDate, LocalDateTime usw. versteht.
     */
    @Bean
    public Module javaTimeModule() {
        return new JavaTimeModule();
    }
}