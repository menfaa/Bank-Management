package com.bank.zahlung.domain;

/**
 * Enum für die verschiedenen Zahlungsarten.
 * Wird zur Unterscheidung von Zahlungen (z.B. in der Diskriminatorspalte) verwendet.
 */
public enum Zahlungsart {
    SEPA,   // SEPA-Überweisung
    KARTE,  // Kartenzahlung
    BAR     // Barzahlung
    // ggf. weitere Typen (z.B. PAYPAL, LASTSCHRIFT, etc.)
}