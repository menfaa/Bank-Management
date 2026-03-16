package com.bank.kunde.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.kunde.domain.Inhaber;
import com.bank.kunde.execptions.NoInhaberFoundException;
import com.bank.kunde.service.InhaberService;

@RestController
@RequestMapping("/api/inhaber")
@CrossOrigin(origins = "http://localhost:3000") // Erlaubt Zugriff vom React-Frontend
public class InhaberController {

    private final InhaberService service;

    public InhaberController(InhaberService service) {
        this.service = service;
    }

    // Alle Girokonten abrufen
    @GetMapping
    public Iterable<Inhaber> allInhaber() {
        return service.findAllInhaber();
    }

    // Einzelnes Girokonto per IBAN abrufen
    @GetMapping("/{inhaberId}")
    public ResponseEntity<?> inhaberByInhaberId(@PathVariable("inhaberId") String inhaberId) {
        Inhaber inhaber = service.findByInhaberId(inhaberId);
        if (inhaber != null) {
            return ResponseEntity.ok(inhaber);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inhaber nicht gefunden");
        }
    }

    // Neues Girokonto anlegen
    @PostMapping
    public ResponseEntity<String> neuerInhaber(@RequestBody Inhaber inhaber) {
        service.save(inhaber);
        return ResponseEntity.status(HttpStatus.CREATED).body("Inhaber erfolgreich angelegt");
    }

    // Konto löschen
    @DeleteMapping("/{inhaberId}")
    public void deleteInhaber(@PathVariable("inhaberId") String inhaberId) {
        service.deleteByInhaberId(inhaberId);

    }

    @ExceptionHandler(NoInhaberFoundException.class)
    public ResponseEntity<String> handleNoInhaberFoundException(NoInhaberFoundException ex) {

        // 404 statt 500 zurückgeben
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Inhaber nicht gefunden: " + ex.getInhaberID().getValue());
    }
}