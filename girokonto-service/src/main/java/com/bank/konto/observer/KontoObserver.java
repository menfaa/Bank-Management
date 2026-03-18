package com.bank.konto.observer;

import com.bank.konto.domain.Girokonto;
import com.bank.konto.domain.Kontoauszug;

/**
 * Das Interface KontoObserver definiert einen Beobachter für Änderungen an einem Girokonto.
 * Implementierende Klassen können auf neue Kontoauszüge reagieren (z.B. Benachrichtigungen versenden).
 */
public interface KontoObserver {
    /**
     * Wird aufgerufen, wenn ein neuer Kontoauszug für das angegebene Girokonto erstellt wurde.
     * @param konto  Das betroffene Girokonto
     * @param auszug Der neue Kontoauszug
     */
    void update(Girokonto konto, Kontoauszug auszug);
}