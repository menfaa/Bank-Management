package com.bank.zahlung.middleware.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(authz -> authz
                                                .requestMatchers(
                                                                "/",
                                                                "/favicon.ico",
                                                                "/css/**", "/js/**", "/images/**", "/webjars/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

                return http.build();
        }

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