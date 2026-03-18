package com.bank.zahlung.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.bank.common.Betrag;

/**
 * Abstrakte Basisklasse für Zahlungen.
 * Wird von Kartenzahlung und SEPAUeberweisung erweitert.
 * Nutzt Single Table Inheritance mit Diskriminatorspalte "zahlungsart".
 */
@Entity // JPA: Markiert die Klasse als persistierbare Entity (Tabelle in der Datenbank)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // JPA: Alle Unterklassen werden in einer Tabelle gespeichert
@DiscriminatorColumn(name = "zahlungsart") // JPA: Spalte zur Unterscheidung der Unterklassen
public abstract class Zahlung {

    @Id // JPA: Primärschlüssel der Tabelle
    @GeneratedValue(strategy = GenerationType.UUID) // JPA: Automatische Generierung einer UUID als ID
    private String id; // Eindeutige ID der Zahlung

    private Betrag betrag; // Betrag der Zahlung (Value Object)
    private LocalDate datum; // Datum der Zahlung
    private String verwendungszweck; // Verwendungszweck der Zahlung

    /**
     * Standardkonstruktor für JPA/Hibernate.
     */
    protected Zahlung() {
    }

    /**
     * Konstruktor für eine neue Zahlung.
     */
    protected Zahlung(Betrag betrag, LocalDate datum, String verwendungszweck, Zahlungsart zahlungsart) {
        this.betrag = betrag;
        this.datum = datum;
        this.verwendungszweck = verwendungszweck;
        // zahlungsart wird durch die Unterklasse/Diskriminator gesetzt
    }

    public Betrag getBetrag() {
        return betrag;
    }

    public void setBetrag(Betrag betrag) {
        this.betrag = betrag;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getVerwendungszweck() {
        return verwendungszweck;
    }

    public void setVerwendungszweck(String verwendungszweck) {
        this.verwendungszweck = verwendungszweck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}