package com.bank.konto.observer;

import com.bank.konto.domain.Girokonto;
import com.bank.konto.domain.Kontoauszug;

/**
 * Ein Observer, der bei Änderungen am Girokonto eine Push-Benachrichtigung simuliert.
 * Gibt eine Nachricht auf der Konsole aus, wenn ein Kontoauszug hinzugefügt wird.
 */
public class PushBenachrichtigungObserver implements KontoObserver {
    @Override
    public void update(Girokonto konto, Kontoauszug auszug) {
        // Bestimmt, ob es sich um eine Einzahlung oder Auszahlung handelt
        String art = auszug.getBetrag().wert() > 0 ? "Einzahlung" : "Auszahlung";
        // Gibt eine simulierte Push-Benachrichtigung auf der Konsole aus
        System.out.println("Push: " + art + " über " + auszug.getBetrag().wert() +
                " EUR auf Konto " + konto.getIban().getValue() +
                " (" + auszug.getVerwendungszweck() + ")");
    }
}