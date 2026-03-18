package com.bank.konto;

import org.springframework.boot.SpringApplication; // Spring Boot: Startet die Anwendung
import org.springframework.boot.autoconfigure.SpringBootApplication; // Spring Boot: Haupt-Annotation für Boot-Anwendungen

/**
 * Einstiegspunkt für die Spring Boot Anwendung GirokontoService.
 * Startet den eingebetteten Server und initialisiert den Spring Application Context.
 */
@SpringBootApplication // Spring Boot: Markiert die Hauptklasse und aktiviert Auto-Konfiguration, Komponenten-Scan etc.
public class GirokontoServiceApplication {

    /**
     * Main-Methode: Startet die Spring Boot Anwendung.
     */
    public static void main(String[] args) {
        SpringApplication.run(GirokontoServiceApplication.class, args);
    }

}