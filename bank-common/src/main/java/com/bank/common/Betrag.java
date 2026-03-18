package com.bank.common; // Definiert das Package, in dem sich die Klasse befindet

import jakarta.persistence.Embeddable; // Importiert die Annotation, um die Klasse als einbettbar zu markieren
import org.jmolecules.ddd.annotation.ValueObject; // Importiert die Annotation für DDD Value Objects

@ValueObject // Markiert die Klasse als Value Object im Sinne von Domain-Driven Design
@Embeddable // Markiert die Klasse als einbettbar für JPA (z.B. in Entities)
public record Betrag(double wert) { // Definiert ein Value Object "Betrag" als Java-Record mit einem Feld "wert"
    // Optional: Validierung im Konstruktor
    public Betrag {
        // Beispiel: Negative Werte erlauben, aber keine NaN oder Infinity
        if (Double.isNaN(wert) || Double.isInfinite(wert)) { // Prüft, ob der Wert ungültig ist
            throw new IllegalArgumentException("Betrag muss eine gültige Zahl sein!"); // Wirft eine Exception bei ungültigem Wert
        }
    }

    // Optional: Hilfsmethoden
    public boolean isPositiv() { // Gibt true zurück, wenn der Betrag positiv ist
        return wert > 0;
    }

    public boolean isNegativ() { // Gibt true zurück, wenn der Betrag negativ ist
        return wert < 0;
    }
}