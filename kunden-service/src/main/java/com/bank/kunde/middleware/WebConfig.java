package com.bank.kunde.middleware;

import com.bank.kunde.middleware.filter.LoggingFilter;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean; // Für die Registrierung von Servlet-Filtern
import org.springframework.context.annotation.Bean; // Markiert Methoden als Beans
import org.springframework.context.annotation.Configuration; // Markiert die Klasse als Konfigurationsklasse
import org.springframework.web.cors.CorsConfiguration; // Für CORS-Konfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // Für CORS-Konfiguration
import org.springframework.web.filter.CorsFilter; // CORS-Filter für Spring
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // Ermöglicht zusätzliche Web-Konfiguration

/**
 * Konfiguriert Web-bezogene Komponenten, insbesondere CORS und Logging-Filter.
 * - Erlaubt CORS-Anfragen vom Girokonto-Service (Port 9000)
 * - Registriert einen Logging-Filter für alle HTTP-Anfragen
 */
@Configuration // Markiert die Klasse als Spring-Konfigurationsklasse
public class WebConfig implements WebMvcConfigurer {

    /**
     * Definiert einen CORS-Filter, der Anfragen vom Girokonto-Service (Port 9000) erlaubt.
     * Löst typische CORS-Probleme bei Microservices.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Erlaubt Anfragen vom Girokonto-Service (Port 9000)
        config.setAllowedOrigins(Arrays.asList("http://localhost:9000"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * Registriert den LoggingFilter für alle HTTP-Anfragen.
     */
    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoggingFilter());
        registrationBean.addUrlPatterns("/*"); // Filter gilt für alle Pfade
        registrationBean.setOrder(1); // Reihenfolge des Filters (niedrigere Zahl = höhere Priorität)
        return registrationBean;
    }

}