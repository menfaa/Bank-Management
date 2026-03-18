package com.bank.zahlung.controller;

import com.bank.zahlung.domain.Zahlung;
import com.bank.zahlung.domain.Kartenzahlung;
import com.bank.zahlung.domain.SEPAUeberweisung;
import com.bank.zahlung.service.ZahlungsService;
import org.springframework.web.bind.annotation.*;

/**
 * REST-Controller für das Zahlungsmanagement.
 * Stellt Endpunkte zum Abrufen und Anlegen von Zahlungen bereit (allgemein, SEPA, Karte).
 */
@RestController // Markiert die Klasse als REST-Controller (JSON/HTTP)
@RequestMapping("/api/payments") // Basis-URL für alle Endpunkte dieser Klasse
@CrossOrigin(origins = "http://localhost:3000") // Erlaubt CORS-Zugriff vom React-Frontend
public class ZahlungsController {

    private final ZahlungsService service; // Service für Zahlungs-Operationen

    /**
     * Konstruktor für Dependency Injection des ZahlungsService.
     */
    public ZahlungsController(ZahlungsService service) {
        this.service = service;
    }

    /**
     * Gibt alle Zahlungen zurück.
     */
    @GetMapping
    public Iterable<Zahlung> alleZahlungen() {
        return service.findAllZahlungen();
    }

    /**
     * Legt eine neue allgemeine Zahlung an.
     */
    @PostMapping
    public void neueZahlung(@RequestBody Zahlung zahlung) {
        service.save(zahlung);
    }

    /**
     * Legt eine neue SEPA-Überweisung an.
     * Optional, falls du einen eigenen Endpunkt für SEPA willst.
     */
    @PostMapping("/sepa")
    public void neueSepaUeberweisung(@RequestBody SEPAUeberweisung sepa) {
        service.save(sepa);
    }

    /**
     * Legt eine neue Kartenzahlung an.
     */
    @PostMapping("/karte")
    public void neueKartenZahlung(@RequestBody Kartenzahlung karte) {
        service.save(karte);
    }

    /**
     * Gibt eine einzelne Zahlung anhand der ID zurück.
     */
    @GetMapping("/{id}")
    public Zahlung getZahlung(@PathVariable String id) {
        return service.findById(id);
    }
}