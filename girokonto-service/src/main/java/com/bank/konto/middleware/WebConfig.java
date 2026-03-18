package com.bank.konto.middleware;

import org.springframework.boot.web.servlet.FilterRegistrationBean; // Spring Boot: Ermöglicht die Registrierung von Servlet-Filtern
import org.springframework.context.annotation.Bean; // Spring: Markiert Methoden als Beans
import org.springframework.context.annotation.Configuration; // Spring: Markiert die Klasse als Konfigurationsklasse
import com.bank.konto.middleware.filter.LoggingFilter; // Importiert den Logging-Filter

/**
 * Konfiguriert Web-bezogene Komponenten, insbesondere die Registrierung des Logging-Filters.
 * Der Filter protokolliert alle eingehenden HTTP-Anfragen.
 */
@Configuration // Spring: Markiert die Klasse als Konfigurationsklasse
public class WebConfig {

    /**
     * Registriert den LoggingFilter für alle HTTP-Anfragen.
     * @return FilterRegistrationBean für LoggingFilter
     */
    @Bean // Spring: Diese Methode liefert eine Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoggingFilter()); // Setzt den Logging-Filter
        registrationBean.addUrlPatterns("/*"); // Filter gilt für alle Pfade
        registrationBean.setOrder(1); // Reihenfolge des Filters (niedrigere Zahl = höhere Priorität)
        return registrationBean;
    }

}