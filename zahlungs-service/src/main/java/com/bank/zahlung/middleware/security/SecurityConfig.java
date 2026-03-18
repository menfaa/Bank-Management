package com.bank.zahlung.middleware.security;

import org.springframework.context.annotation.Bean; // Markiert Methoden als Beans für Spring
import org.springframework.context.annotation.Configuration; // Markiert die Klasse als Konfigurationsklasse
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // Aktiviert Methodensicherheit
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Für HTTP-Sicherheitskonfiguration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Aktiviert Web-Sicherheit
import org.springframework.security.config.http.SessionCreationPolicy; // Steuerung der Session-Strategie
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain; // Filterkette für HTTP-Anfragen

/**
 * Sicherheitskonfiguration für den Zahlungsservice.
 * - Aktiviert Web- und Methodensicherheit.
 * - Konfiguriert JWT-Authentifizierung mit Keycloak.
 * - Erlaubt öffentliche und geschützte Endpunkte.
 */
@Configuration // Markiert die Klasse als Spring-Konfigurationsklasse
@EnableWebSecurity // Aktiviert Web-Sicherheit
@EnableMethodSecurity // Aktiviert Methodensicherheit (z.B. @PreAuthorize)
public class SecurityConfig {

        /**
         * Definiert die Sicherheitsfilterkette für HTTP-Anfragen.
         * - CSRF-Schutz deaktiviert (für APIs typisch)
         * - Session-Management auf "stateless" (keine HTTP-Session)
         * - Öffentliche und geschützte Endpunkte
         * - JWT-Authentifizierung als Resource Server
         */
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // CSRF-Schutz deaktivieren
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Keine HTTP-Session
                                .authorizeHttpRequests(authz -> authz
                                                .requestMatchers(
                                                                "/", // Startseite
                                                                "/favicon.ico", // Favicon
                                                                "/css/**", "/js/**", "/images/**", "/webjars/**") // Statische Ressourcen
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
        @Bean
        public JwtDecoder jwtDecoder() {
                String jwkSetUri = "http://keycloak:8080/realms/myRealm/protocol/openid-connect/certs";
                NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();

                String expectedIssuer = "http://localhost:8081/realms/myRealm";
                OAuth2TokenValidator<Jwt> issuerValidator = new JwtIssuerValidator(expectedIssuer);
                OAuth2TokenValidator<Jwt> withTimestamp = JwtValidators.createDefault();
                OAuth2TokenValidator<Jwt> combinedValidator = new DelegatingOAuth2TokenValidator<>(withTimestamp,
                                issuerValidator);

                jwtDecoder.setJwtValidator(combinedValidator);
                return jwtDecoder;
        }
}