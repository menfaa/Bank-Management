package com.bank.konto.config; // Definiert das Package, in dem sich die Klasse befindet

import com.fasterxml.jackson.databind.Module; // Importiert das Jackson-Modul-Interface
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Importiert das Modul für Java 8 Date/Time API Unterstützung
import org.springframework.context.annotation.Bean; // Importiert die Bean-Annotation für Spring
import org.springframework.context.annotation.Configuration; // Importiert die Configuration-Annotation für Spring

@Configuration // Markiert die Klasse als Konfigurationsklasse für Spring
public class JacksonConfig {
    @Bean // Markiert die Methode als Bean, die von Spring verwaltet wird
    public Module javaTimeModule() { // Definiert eine Bean vom Typ Module (für Jackson)
        return new JavaTimeModule(); // Gibt ein neues JavaTimeModule zurück, das die Unterstützung für Java 8 Zeittypen (z.B. LocalDate) in Jackson aktiviert
    }
}