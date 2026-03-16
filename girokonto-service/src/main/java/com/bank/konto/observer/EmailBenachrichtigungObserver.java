package com.bank.konto.observer;

import com.bank.konto.domain.Girokonto;
import com.bank.konto.domain.Kontoauszug;

public class EmailBenachrichtigungObserver implements KontoObserver {
    @Override
    public void update(Girokonto konto, Kontoauszug auszug) {
        String art = auszug.getBetrag().wert() > 0 ? "Einzahlung" : "Auszahlung";
        System.out.println("E-Mail: " + art + " über " + auszug.getBetrag().wert() +
                " EUR auf Konto " + konto.getIban().getValue() +
                " (" + auszug.getVerwendungszweck() + ")");
    }
}