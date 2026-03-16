package com.bank.konto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.bank.common.IBAN;
import com.bank.common.events.PaymentRequestedEvent;
import com.bank.konto.PaymentRequestDTO;
import com.bank.konto.domain.Girokonto;
import com.bank.konto.execptions.NoGirokontoFoundException;
import com.bank.konto.service.GirokontoService;
import com.bank.konto.strategy.KartenPaymentStrategy;
import com.bank.konto.strategy.PaymentStrategy;

import org.springframework.http.*;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/girokonten")
@CrossOrigin(origins = "http://localhost:3000") // Erlaubt Zugriff vom React-Frontend
public class GirokontoController {

    private final GirokontoService service;

    private RestTemplate restTemplate;

    private final KafkaTemplate<String, PaymentRequestedEvent> kafkaTemplate;

    @Value("${kunden.service.url}")
    private String kundenServiceUrl;

    public GirokontoController(GirokontoService service, RestTemplate restTemplate,
            KafkaTemplate<String, PaymentRequestedEvent> kafkaTemplate) {
        this.service = service;
        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    // Alle Girokonten abrufen
    @GetMapping
    public Iterable<Girokonto> alleGirokonten() {
        return service.findAllGirokontos();
    }

    // Einzelnes Girokonto per IBAN abrufen
    @GetMapping("/{iban}")
    public Girokonto kontoByIban(@PathVariable IBAN iban) {
        return service.findByIban(iban.getValue());
    }

    // Einzelnes Girokonto per IBAN abrufen
    @GetMapping("/kontoauszuege/{iban}")
    public Optional<Girokonto> kontoByIbanithKontoauszuege(@PathVariable String iban) {
        return service.findByIbanWithKontoauszuege(iban);
    }

    // Neues Girokonto anlegen
    @PostMapping
    public ResponseEntity<?> neuesKonto(@RequestBody Girokonto girokonto) {
        // 1. Inhaber ID aus dem Request extrahieren
        String inhaberId = girokonto.getInhaberId().getValue();

        try {
            // 2. JWT aus dem aktuellen SecurityContext holen (Token Propagation)
            JwtAuthenticationToken auth = (JwtAuthenticationToken) SecurityContextHolder.getContext()
                    .getAuthentication();
            String jwtToken = auth.getToken().getTokenValue();

            // 3. Header für den internen Request an den kunden-service erstellen
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(jwtToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            // 4. Kunden-Service mit Token aufrufen
            String url = kundenServiceUrl + inhaberId;
            ResponseEntity<Object> inhaberResponse = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Object.class);

            // 5. Erfolg prüfen und speichern
            if (inhaberResponse.getStatusCode().is2xxSuccessful()) {
                service.save(girokonto);
                return ResponseEntity.status(HttpStatus.CREATED).body(girokonto);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Inhaber existiert nicht!");
            }

        } catch (HttpClientErrorException.Unauthorized e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Service-to-Service Authentifizierung fehlgeschlagen.");
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Inhaber mit ID " + inhaberId + " wurde nicht gefunden.");
        } catch (Exception e) {
            // Logge den Fehler für das Debugging (e.printStackTrace() oder Logger)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fehler bei der Kommunikation mit dem Kunden-Service: " + e.getMessage());
        }
    }

    @PostMapping("/{iban}/kartenpayment")
    public void requestPayment(
            @PathVariable String iban,
            @RequestBody PaymentRequestDTO paymentRequest) {

        PaymentRequestedEvent event = new PaymentRequestedEvent(
                iban,
                paymentRequest.getBetrag(),
                paymentRequest.getVerwendungszweck(),
                paymentRequest.getKartennummer(),
                paymentRequest.getHaendler(),
                paymentRequest.getDatum() != null ? paymentRequest.getDatum() : LocalDate.now());

        // Lade das Girokonto
        Girokonto girokonto = service.findByIban(iban);

        PaymentStrategy strategy = new KartenPaymentStrategy(event.getKartennummer(),
                event.getHaendler(),
                kafkaTemplate);
        strategy.executePayment(girokonto, event.getBetrag(), event.getVerwendungszweck());
    }

    // Konto löschen
    @DeleteMapping("/{iban}")
    public void deleteKonto(@PathVariable IBAN iban) {
        service.deleteByIban(iban.getValue());
    }

    @ExceptionHandler(NoGirokontoFoundException.class)
    public ResponseEntity<String> handleNoGirokontoFoundException(NoGirokontoFoundException ex) {

        return ResponseEntity.internalServerError().body("No Girokonto found with iban: " + ex.getIban().getValue());
    }
}