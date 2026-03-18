package com.bank.konto.strategy;

import com.bank.common.Betrag;
import com.bank.konto.domain.Girokonto;

/**
 * Das Strategy-Interface für verschiedene Zahlungsarten.
 * Implementierende Klassen definieren, wie eine Zahlung ausgeführt wird.
 */
public interface PaymentStrategy {
    /**
     * Führt eine Zahlung für das angegebene Girokonto aus.
     * @param girokonto Das betroffene Girokonto
     * @param betrag Der zu zahlende Betrag
     * @param verwendungszweck Der Verwendungszweck der Zahlung
     */
    void executePayment(Girokonto girokonto, Betrag betrag, String verwendungszweck);
}