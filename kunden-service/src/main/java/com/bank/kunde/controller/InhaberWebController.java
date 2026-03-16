package com.bank.kunde.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bank.kunde.domain.Inhaber;
import com.bank.kunde.service.InhaberService;

@Controller
@RequestMapping("/inhaber")
public class InhaberWebController {

    @Autowired
    InhaberService inhaberService;

    @GetMapping()
    public String inhaberPage(Model model) {
        Iterable<Inhaber> inhaber = inhaberService.findAllInhaber();
        model.addAttribute("inhaber", inhaber); // Liste direkt übergeben
        return "inhaber"; // Liefert src/main/resources/templates/inhaber.html
    }
}