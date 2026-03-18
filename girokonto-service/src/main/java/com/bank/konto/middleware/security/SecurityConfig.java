package com.bank.konto.middleware.security;

import org.springframework.context.annotation.Bean; // Spring: Markiert Methoden als Beans
import org.springframework.context.annotation.Configuration; // Spring: Markiert die Klasse als Konfigurationsklasse
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // Spring Security: Aktiviert Methodensicherheit
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Spring Security: Konfiguration für HTTP-Sicherheit
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Spring Security: Aktiviert Web-Sicherheit
import org.springframework.security.config.http.SessionCreationPolicy; // Spring Security: Steuerung der Session-Strategie
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain; // Spring Security: Filterkette für HTTP-Anfragen

/**
 * Sicherheitskonfiguration für die Anwendung.
 * - Aktiviert Web- und Methodensicherheit.
 * - Konfiguriert JWT-Authentifizierung mit Keycloak.
 * - Erlaubt öffentliche und geschützte Endpunkte.
 */
@Configuration // Spring: Markiert die Klasse als Konfigurationsklasse
@EnableWebSecurity // Spring Security: Aktiviert Web-Sicherheit
@EnableMethodSecurity // Spring Security: Aktiviert Methodensicherheit (z.B. @PreAuthorize)
public class SecurityConfig {

        /**
         * Definiert die Sicherheitsfilterkette für HTTP-Anfragen.
         * - CSRF-Schutz deaktiviert (für APIs typisch)
         * - Session-Management auf "stateless" (keine HTTP-Session)
         * - Öffentliche und geschützte Endpunkte
         * - JWT-Authentifizierung als Resource Server
         */
        @Bean // Spring: Diese Methode liefert eine Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // CSRF-Schutz deaktivieren
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Keine HTTP-Session
                                .authorizeHttpRequests(authz -> authz
                                                .requestMatchers(
                                                                "/", "/index.html", "/girokonten", "/girokonten.html",
                                                                "/favicon.ico",
                                                                "/css/**", "/js/**", "/images/**", "/webjars/**")
                                                .permitAll() // Diese Pfade sind öffentlich zugänglich
                                                .anyRequest().authenticated()) // Alle anderen Anfragen benötigen Authentifizierung
                                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())); // JWT-Authentifizierung

                return http.build();
        }

        /**
         * Konfiguriert den JWT-Decoder für die Anwendung.
         * - Lädt die öffentlichen Schlüssel von Keycloak (JWK Set URI)
         * - Validiert den Aussteller (Issuer)
         * - Nutzt Standard- und benutzerdefinierte Validatoren
         */
        @Bean // Spring: Diese Methode liefert eine Bean
        public JwtDecoder jwtDecoder() {
                String jwkSetUri = "http://keycloak:8080/realms/myRealm/protocol/openid-connect/certs"; // URI für die öffentlichen Schlüssel
                NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();

                String expectedIssuer = "http://localhost:8081/realms/myRealm"; // Erwarteter Aussteller
                OAuth2TokenValidator<Jwt> issuerValidator = new JwtIssuerValidator(expectedIssuer); // Validator für den Aussteller
                OAuth2TokenValidator<Jwt> withTimestamp = JwtValidators.createDefault(); // Standard-Validatoren (z.B. Ablaufzeit)
                OAuth2TokenValidator<Jwt> combinedValidator = new DelegatingOAuth2TokenValidator<>(withTimestamp,
                                issuerValidator); // Kombiniert beide Validatoren

                jwtDecoder.setJwtValidator(combinedValidator); // Setzt den kombinierten Validator
                return jwtDecoder;
        }
}