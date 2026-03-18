package com.bank.konto.controller; // Definiert das Package, in dem sich die Klasse befindet

import org.springframework.beans.factory.annotation.Value; // Importiert die Annotation für das Einlesen von Properties
import org.springframework.kafka.core.KafkaTemplate; // Importiert KafkaTemplate für das Senden von Events
import org.springframework.security.core.context.SecurityContextHolder; // Importiert SecurityContextHolder für Security-Kontext
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken; // Importiert JWT-Token-Klasse
import org.springframework.web.bind.annotation.*; // Importiert Spring Web-Annotationen
import org.springframework.web.client.HttpClientErrorException; // Importiert Exception für HTTP-Fehler
import org.springframework.web.client.RestTemplate; // Importiert RestTemplate für HTTP-Anfragen

import com.bank.common.IBAN; // Importiert die IBAN-Klasse
import com.bank.common.events.PaymentRequestedEvent; // Importiert das Event für Zahlungsanfragen
import com.bank.konto.PaymentRequestDTO; // Importiert das DTO für Zahlungsanfragen
import com.bank.konto.domain.Girokonto; // Importiert die Girokonto-Klasse
import com.bank.konto.execptions.NoGirokontoFoundException; // Importiert die Exception für nicht gefundene Konten
import com.bank.konto.service.GirokontoService; // Importiert den Service für Girokonten
import com.bank.konto.strategy.KartenPaymentStrategy; // Importiert die Karten-Zahlungsstrategie
import com.bank.konto.strategy.PaymentStrategy; // Importiert das PaymentStrategy-Interface

import org.springframework.http.*; // Importiert HTTP-Klassen
import java.time.LocalDate; // Importiert LocalDate für Datumsangaben
import java.util.Optional; // Importiert Optional für optionale Rückgaben

@RestController // Markiert die Klasse als REST-Controller
@RequestMapping("/api/girokonten") // Basis-URL für alle Endpunkte dieser Klasse
@CrossOrigin(origins = "http://localhost:3000") // Erlaubt CORS-Zugriff vom React-Frontend
public class GirokontoController {

    private final GirokontoService service; // Service für Girokonten

    private RestTemplate restTemplate; // RestTemplate für HTTP-Anfragen

    private final KafkaTemplate<String, PaymentRequestedEvent> kafkaTemplate; // KafkaTemplate für Events

    @Value("${kunden.service.url}") // Liest die URL des Kunden-Services aus den Properties
    private String kundenServiceUrl;

    // Konstruktor für Dependency Injection
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

    // Einzelnes Girokonto mit Kontoauszügen per IBAN abrufen
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

    // Karten-Zahlung anfragen
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

        // Setze die Karten-Zahlungsstrategie
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

    // Exception-Handler für nicht gefundene Konten
    @ExceptionHandler(NoGirokontoFoundException.class)
    public ResponseEntity<String> handleNoGirokontoFoundException(NoGirokontoFoundException ex) {
        return ResponseEntity.internalServerError().body("No Girokonto found with iban: " + ex.getIban().getValue());
    }
}