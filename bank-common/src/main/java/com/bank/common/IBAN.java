package com.bank.common; // Definiert das Package, in dem sich die Klasse befindet

import jakarta.persistence.Column; // Importiert die Annotation für Spaltenzuordnung in JPA
import jakarta.persistence.Embeddable; // Importiert die Annotation, um die Klasse als einbettbar zu markieren

import java.util.Objects; // Importiert Hilfsmethoden für equals und hashCode

import org.jmolecules.ddd.annotation.ValueObject; // Importiert die Annotation für DDD Value Objects

// Value Object für IBAN
@ValueObject // Markiert die Klasse als Value Object im Sinne von Domain-Driven Design
@Embeddable // Markiert die Klasse als einbettbar für JPA (z.B. in Entities)
public class IBAN {

    @Column(name = "iban") // Ordnet das Feld einer Datenbankspalte zu
    private String value; // Speichert den Wert der IBAN

    protected IBAN() { // Geschützter Standardkonstruktor (wird von JPA benötigt)
    }

    public IBAN(String value) { // Öffentlicher Konstruktor zum Setzen des Werts
        this.value = value;
    }

    public String getValue() { // Getter für den Wert
        return value;
    }

    @Override
    public String toString() { // Überschreibt toString, gibt den Wert zurück
        return value;
    }

    @Override
    public boolean equals(Object o) { // Überschreibt equals für Vergleich auf Basis des Werts
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        IBAN iban = (IBAN) o;
        return Objects.equals(value, iban.value);
    }

    @Override
    public int hashCode() { // Überschreibt hashCode, basiert auf value
        return Objects.hash(value);
    }
}