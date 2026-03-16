package com.bank.konto.context;

import com.bank.common.Betrag;
import com.bank.konto.domain.Girokonto;
import com.bank.konto.strategy.PaymentStrategy;

public class PaymentContext {
    private PaymentStrategy strategy;

    // Konstruktor oder Setter für die Strategie
    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    // Führt die Zahlung mit der aktuell gesetzten Strategie aus
    public void executePayment(Girokonto girokonto, Betrag betrag, String verwendungszweck) {
        if (strategy == null) {
            throw new IllegalStateException("Keine Zahlungsstrategie gesetzt!");
        }
        strategy.executePayment(girokonto, betrag, verwendungszweck);
    }
}
