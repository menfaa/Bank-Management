package com.bank.konto.controller;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeWebController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal Jwt jwt, Model model) {
        // Optional: User-Info an die View übergeben
        if (jwt != null) {
            model.addAttribute("username", jwt.getSubject());
            model.addAttribute("email", jwt.getClaim("email"));
        }
        return "index"; // Liefert src/main/resources/templates/index.html (bei Thymeleaf)
    }

}
