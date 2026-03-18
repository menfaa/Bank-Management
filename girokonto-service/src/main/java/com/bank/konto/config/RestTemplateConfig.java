package com.bank.konto.config; // Definiert das Package, in dem sich die Klasse befindet

import org.springframework.context.annotation.Bean; // Importiert die Bean-Annotation für Spring
import org.springframework.context.annotation.Configuration; // Importiert die Configuration-Annotation für Spring
import org.springframework.web.client.RestTemplate; // Importiert die RestTemplate-Klasse für HTTP-Anfragen

@Configuration // Markiert die Klasse als Konfigurationsklasse für Spring
public class RestTemplateConfig {
    @Bean // Markiert die Methode als Bean, die von Spring verwaltet wird
    public RestTemplate restTemplate() { // Erstellt und liefert eine neue Instanz von RestTemplate
        return new RestTemplate();
    }
}