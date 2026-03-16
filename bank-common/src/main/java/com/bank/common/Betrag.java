package com.bank.common;

import jakarta.persistence.Embeddable;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
@Embeddable
public record Betrag(double wert) {
    // Optional: Validierung im Konstruktor
    public Betrag {
        // Beispiel: Negative Werte erlauben, aber keine NaN oder Infinity
        if (Double.isNaN(wert) || Double.isInfinite(wert)) {
            throw new IllegalArgumentException("Betrag muss eine gültige Zahl sein!");
        }
    }

    // Optional: Hilfsmethoden
    public boolean isPositiv() {
        return wert > 0;
    }

    public boolean isNegativ() {
        return wert < 0;
    }
}