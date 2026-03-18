package com.bank.kunde.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.kunde.domain.Inhaber;
import com.bank.kunde.execptions.NoInhaberFoundException;
import com.bank.kunde.service.InhaberService;

/**
 * REST-Controller für das Inhaber-Management.
 * Stellt Endpunkte zum Abrufen, Anlegen und Löschen von Inhabern bereit.
 */
@RestController // Markiert die Klasse als REST-Controller (JSON/HTTP)
@RequestMapping("/api/inhaber") // Basis-URL für alle Endpunkte dieser Klasse
@CrossOrigin(origins = "http://localhost:3000") // Erlaubt CORS-Zugriff vom React-Frontend
public class InhaberController {

    private final InhaberService service; // Service für Inhaber-Operationen

    /**
     * Konstruktor für Dependency Injection des InhaberService.
     */
    public InhaberController(InhaberService service) {
        this.service = service;
    }

    /**
     * Gibt alle Inhaber zurück.
     */
    @GetMapping
    public Iterable<Inhaber> allInhaber() {
        return service.findAllInhaber();
    }

    /**
     * Gibt einen Inhaber anhand der Inhaber-ID zurück.
     * @param inhaberId Die ID des Inhabers
     * @return ResponseEntity mit Inhaber oder Fehlermeldung
     */
    @GetMapping("/{inhaberId}")
    public ResponseEntity<?> inhaberByInhaberId(@PathVariable("inhaberId") String inhaberId) {
        Inhaber inhaber = service.findByInhaberId(inhaberId);
        if (inhaber != null) {
            return ResponseEntity.ok(inhaber);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inhaber nicht gefunden");
        }
    }

    /**
     * Legt einen neuen Inhaber an.
     * @param inhaber Inhaber-Objekt aus dem Request-Body
     * @return ResponseEntity mit Statusmeldung
     */
    @PostMapping
    public ResponseEntity<String> neuerInhaber(@RequestBody Inhaber inhaber) {
        service.save(inhaber);
        return ResponseEntity.status(HttpStatus.CREATED).body("Inhaber erfolgreich angelegt");
    }

    /**
     * Löscht einen Inhaber anhand der Inhaber-ID.
     * @param inhaberId Die ID des Inhabers
     */
    @DeleteMapping("/{inhaberId}")
    public void deleteInhaber(@PathVariable("inhaberId") String inhaberId) {
        service.deleteByInhaberId(inhaberId);
    }

    /**
     * Exception-Handler für den Fall, dass kein Inhaber gefunden wurde.
     * Gibt HTTP 404 zurück.
     */
    @ExceptionHandler(NoInhaberFoundException.class)
    public ResponseEntity<String> handleNoInhaberFoundException(NoInhaberFoundException ex) {
        // 404 statt 500 zurückgeben
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Inhaber nicht gefunden: " + ex.getInhaberID().getValue());
    }
}