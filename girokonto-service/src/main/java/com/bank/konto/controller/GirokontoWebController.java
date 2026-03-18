package com.bank.konto.controller; // Definiert das Package, in dem sich die Klasse befindet

import org.springframework.stereotype.Controller; // Importiert die Controller-Annotation für Spring MVC
import org.springframework.ui.Model; // Importiert das Model-Interface für die Übergabe von Daten an die View

import org.springframework.beans.factory.annotation.Autowired; // Importiert die Autowired-Annotation für Dependency Injection

import org.springframework.web.bind.annotation.GetMapping; // Importiert die GetMapping-Annotation für GET-Requests
import org.springframework.web.bind.annotation.RequestMapping; // Importiert die RequestMapping-Annotation für die Basis-URL

import com.bank.konto.domain.Girokonto; // Importiert die Girokonto-Klasse
import com.bank.konto.service.GirokontoService; // Importiert den Service für Girokonten

@Controller // Markiert die Klasse als Spring MVC Controller
@RequestMapping("/girokonten") // Basis-URL für alle Endpunkte dieser Klasse
public class GirokontoWebController {

    @Autowired // Automatische Dependency Injection des GirokontoService
    GirokontoService girokontoService;

    @GetMapping() // Verarbeitet GET-Anfragen auf "/girokonten"
    public String girokontenPage(Model model) {
        Iterable<Girokonto> girokonten = girokontoService.findAllGirokontos(); // Holt alle Girokonten aus dem Service
        model.addAttribute("girokonten", girokonten); // Fügt die Liste dem Model hinzu, damit sie in der View verfügbar ist
        return "girokonten"; // Gibt den Namen der View zurück (z.B. src/main/resources/templates/girokonten.html)
    }
}