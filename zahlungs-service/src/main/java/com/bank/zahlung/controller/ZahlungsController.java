package com.bank.zahlung.controller;

import com.bank.zahlung.domain.Zahlung;
import com.bank.zahlung.domain.Kartenzahlung;
import com.bank.zahlung.domain.SEPAUeberweisung;
import com.bank.zahlung.service.ZahlungsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class ZahlungsController {

    private final ZahlungsService service;

    public ZahlungsController(ZahlungsService service) {
        this.service = service;
    }

    // Alle Zahlungen abrufen
    @GetMapping
    public Iterable<Zahlung> alleZahlungen() {
        return service.findAllZahlungen();
    }

    // Neue allgemeine Zahlung anlegen
    @PostMapping
    public void neueZahlung(@RequestBody Zahlung zahlung) {
        service.save(zahlung);
    }

    // Neue SEPA-Überweisung anlegen (optional, wenn du einen eigenen Endpunkt
    // willst)
    @PostMapping("/sepa")
    public void neueSepaUeberweisung(@RequestBody SEPAUeberweisung sepa) {
        service.save(sepa);
    }

    @PostMapping("/karte")
    public void neueKartenZahlung(@RequestBody Kartenzahlung karte) {
        service.save(karte);
    }

    // Einzelne Zahlung abrufen
    @GetMapping("/{id}")
    public Zahlung getZahlung(@PathVariable String id) {
        return service.findById(id);
    }
}