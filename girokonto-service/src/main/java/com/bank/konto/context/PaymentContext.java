package com.bank.konto.context; // Definiert das Package, in dem sich die Klasse befindet

import com.bank.common.Betrag; // Importiert die Klasse Betrag
import com.bank.konto.domain.Girokonto; // Importiert die Klasse Girokonto
import com.bank.konto.strategy.PaymentStrategy; // Importiert das PaymentStrategy-Interface

public class PaymentContext { // Definiert die PaymentContext-Klasse (Kontext für die Strategie)
    private PaymentStrategy strategy; // Feld für die aktuelle Zahlungsstrategie

    // Konstruktor oder Setter für die Strategie
    public PaymentContext(PaymentStrategy strategy) { // Konstruktor, der die Strategie setzt
        this.strategy = strategy;
    }

    public void setStrategy(PaymentStrategy strategy) { // Setter, um die Strategie zur Laufzeit zu ändern
        this.strategy = strategy;
    }

    // Führt die Zahlung mit der aktuell gesetzten Strategie aus
    public void executePayment(Girokonto girokonto, Betrag betrag, String verwendungszweck) {
        if (strategy == null) { // Prüft, ob eine Strategie gesetzt ist
            throw new IllegalStateException("Keine Zahlungsstrategie gesetzt!"); // Fehler, falls keine Strategie vorhanden ist
        }
        strategy.executePayment(girokonto, betrag, verwendungszweck); // Führt die Zahlung mit der aktuellen Strategie aus
    }
}