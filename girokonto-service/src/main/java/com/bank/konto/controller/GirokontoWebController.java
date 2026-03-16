package com.bank.konto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bank.konto.domain.Girokonto;
import com.bank.konto.service.GirokontoService;

@Controller
@RequestMapping("/girokonten")
public class GirokontoWebController {

    @Autowired
    GirokontoService girokontoService;

    @GetMapping()
    public String girokontenPage(Model model) {
        Iterable<Girokonto> girokonten = girokontoService.findAllGirokontos();
        model.addAttribute("girokonten", girokonten); // Liste direkt übergeben
        return "girokonten"; // Liefert src/main/resources/templates/girokonten.html
    }
}