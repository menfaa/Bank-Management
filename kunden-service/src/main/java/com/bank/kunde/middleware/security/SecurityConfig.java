package com.bank.kunde.middleware.security;

import org.springframework.context.annotation.Bean; // Spring: Markiert Methoden als Beans
import org.springframework.context.annotation.Configuration; // Spring: Markiert die Klasse als Konfigurationsklasse
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // Spring Security: Aktiviert Methodensicherheit
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Spring Security: Konfiguration für HTTP-Sicherheit
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Spring Security: Aktiviert Web-Sicherheit
import org.springframework.security.config.http.SessionCreationPolicy; // Spring Security: Steuerung der Session-Strategie
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain; // Spring Security: Filterkette für HTTP-Anfragen
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Sicherheitskonfiguration für die Anwendung.
 * - Aktiviert Web- und Methodensicherheit.
 * - Konfiguriert JWT-Authentifizierung mit Keycloak.
 * - Erlaubt CORS für das Frontend.
 * - Erlaubt OPTIONS-Requests (Preflight) ohne Authentifizierung.
 */
@Configuration // Spring: Markiert die Klasse als Konfigurationsklasse
@EnableWebSecurity // Spring Security: Aktiviert Web-Sicherheit
@EnableMethodSecurity // Spring Security: Aktiviert Methodensicherheit (z.B. @PreAuthorize)
public class SecurityConfig {

        /**
         * Definiert die Sicherheitsfilterkette für HTTP-Anfragen.
         * - CORS aktiviert
         * - CSRF deaktiviert (für APIs typisch)
         * - Session-Management auf "stateless" (keine HTTP-Session)
         * - Öffentliche und geschützte Endpunkte
         * - JWT-Authentifizierung als Resource Server
         */
        @Bean // Spring: Diese Methode liefert eine Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                // 1. CORS-Filter aktivieren (nutzt die corsConfigurationSource-Bean weiter unten)
                                .cors(Customizer.withDefaults())
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(authz -> authz
                                                // 2. Erlaube OPTIONS-Requests (Preflight) IMMER ohne Authentifizierung
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                .requestMatchers("/", "/inhaber", "/favicon.ico", "/css/**",
                                                                "/js/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

                return http.build();
        }

        /**
         * Definition der CORS-Regeln für den Browser.
         * Erlaubt Zugriffe vom Frontend (localhost:3000) und lokale HTML-Dateien.
         */
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                // Erlaube dein Frontend (und "null" für lokale HTML-Dateien)
                configuration.setAllowedOrigins(List.of("http://localhost:3000", "null", "http://127.0.0.1:3000"));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "X-Requested-With"));
                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
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
                OAuth2TokenValidator<Jwt> combinedValidator = new DelegatingOAuth2TokenValidator<>(
                                withTimestamp, issuerValidator);

                jwtDecoder.setJwtValidator(combinedValidator);
                return jwtDecoder;
        }
}