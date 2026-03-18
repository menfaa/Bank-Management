package com.bank.konto.controller; // Definiert das Package, in dem sich die Klasse befindet

import org.springframework.security.oauth2.jwt.Jwt; // Importiert die Jwt-Klasse für JWT-Token
import org.springframework.security.core.annotation.AuthenticationPrincipal; // Importiert die Annotation für das aktuelle Authentifizierungs-Objekt
import org.springframework.stereotype.Controller; // Importiert die Controller-Annotation für Spring MVC
import org.springframework.ui.Model; // Importiert das Model-Interface für die Übergabe von Daten an die View
import org.springframework.web.bind.annotation.GetMapping; // Importiert die GetMapping-Annotation für GET-Requests

@Controller // Markiert die Klasse als Spring MVC Controller
public class HomeWebController {

    @GetMapping("/") // Verarbeitet GET-Anfragen auf die Startseite "/"
    public String index(@AuthenticationPrincipal Jwt jwt, Model model) {
        // Optional: User-Info an die View übergeben
        if (jwt != null) { // Prüft, ob ein JWT-Token vorhanden ist (User eingeloggt)
            model.addAttribute("username", jwt.getSubject()); // Fügt den Benutzernamen dem Model hinzu
            model.addAttribute("email", jwt.getClaim("email")); // Fügt die E-Mail dem Model hinzu
        }
        return "index"; // Gibt den Namen der View zurück (z.B. src/main/resources/templates/index.html bei Thymeleaf)
    }

}