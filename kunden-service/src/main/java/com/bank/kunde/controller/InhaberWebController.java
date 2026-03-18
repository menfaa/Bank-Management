package com.bank.kunde.controller;

import org.springframework.stereotype.Controller; // Markiert die Klasse als Spring MVC Controller
import org.springframework.ui.Model; // Ermöglicht das Übergeben von Daten an die View

import org.springframework.beans.factory.annotation.Autowired; // Für Dependency Injection

import org.springframework.web.bind.annotation.GetMapping; // Für GET-Requests
import org.springframework.web.bind.annotation.RequestMapping; // Für Basis-URL

import com.bank.kunde.domain.Inhaber;
import com.bank.kunde.service.InhaberService;

/**
 * Web-Controller für die Anzeige aller Inhaber im Browser (Thymeleaf-View).
 * Stellt eine HTML-Seite mit allen Inhabern bereit.
 */
@Controller // Markiert die Klasse als Spring MVC Controller
@RequestMapping("/inhaber") // Basis-URL für alle Endpunkte dieser Klasse
public class InhaberWebController {

    @Autowired // Automatische Dependency Injection des InhaberService
    InhaberService inhaberService;

    /**
     * Zeigt die Seite mit allen Inhabern an.
     * @param model Model für die Übergabe der Inhaber-Liste an die View
     * @return Name der View (inhaber.html)
     */
    @GetMapping()
    public String inhaberPage(Model model) {
        Iterable<Inhaber> inhaber = inhaberService.findAllInhaber(); // Holt alle Inhaber aus dem Service
        model.addAttribute("inhaber", inhaber); // Übergibt die Liste an die View
        return "inhaber"; // Liefert src/main/resources/templates/inhaber.html
    }
}