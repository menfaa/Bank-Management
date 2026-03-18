package com.bank.konto.domain;

/**
 * Die Klasse Kontoauszug repräsentiert einen einzelnen Buchungsposten (Transaktion) auf einem Girokonto.
 * Sie ist als JPA-Entity und DDD-Entity modelliert und speichert Informationen wie IBAN, Datum, Betrag und Verwendungszweck.
 * Durch die Verwendung von Vererbung (SINGLE_TABLE) können verschiedene Arten von Kontoauszügen in einer Tabelle gespeichert werden.
 *
 * Annotation-Erklärungen:
 * @jakarta.persistence.Entity        - Markiert die Klasse als JPA-Entity, sodass sie von JPA/Hibernate verwaltet und in einer Datenbanktabelle gespeichert wird.
 * @org.jmolecules.ddd.annotation.Entity - Markiert die Klasse als Entity im Sinne von Domain-Driven Design (DDD).
 * @Inheritance(strategy = InheritanceType.SINGLE_TABLE) - Gibt an, dass Vererbung in einer einzigen Tabelle abgebildet wird.
 * @DiscriminatorColumn(name = "transaktionsart") - Fügt eine Spalte "transaktionsart" hinzu, um zwischen Unterklassen zu unterscheiden.
 */

import jakarta.persistence.*;
import org.jmolecules.ddd.annotation.Identity;

import com.bank.common.Betrag;
import com.bank.common.IBAN;

import java.time.LocalDate;

@jakarta.persistence.Entity // JPA-Entity: Wird von JPA/Hibernate als Datenbanktabelle behandelt
@org.jmolecules.ddd.annotation.Entity // DDD-Entity: Markiert die Klasse als Entity im DDD-Kontext
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Vererbung: Alle Unterklassen werden in einer Tabelle gespeichert
@DiscriminatorColumn(name = "transaktionsart") // Diskriminatorspalte zur Unterscheidung von Unterklassen
public class Kontoauszug {

    @Id // Primärschlüssel der Tabelle
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatische Generierung der ID (Auto-Increment)
    @Identity // DDD: Markiert dieses Feld als Identität der Entity
    private Long id; // Eindeutige ID des Kontoauszugs

    @Embedded // Das Feld ist ein eingebettetes Value Object (IBAN)
    @AttributeOverride(name = "value", column = @Column(name = "girokonto_iban", nullable = false)) // Überschreibt den Spaltennamen für das Feld "value" in IBAN zu "girokonto_iban"
    private IBAN girokontoIban; // IBAN des zugehörigen Girokontos

    @Column(nullable = false) // Spalte darf nicht null sein
    private LocalDate datum; // Buchungsdatum

    @Column(nullable = false) // Spalte darf nicht null sein
    @Embedded // Betrag als eingebettetes Value Object
    @AttributeOverride(name = "wert", column = @Column(name = "betrag")) // Überschreibt den Spaltennamen für das Feld "wert" in Betrag zu "betrag"
    private Betrag betrag; // Betrag (positiv = Einzahlung, negativ = Auszahlung)

    @Column(nullable = false) // Spalte darf nicht null sein
    private String verwendungszweck; // Verwendungszweck

    protected Kontoauszug() {
        // Für JPA (Standardkonstruktor)
    }

    public Kontoauszug(IBAN girokontoIban, LocalDate datum, Betrag betrag, String verwendungszweck) {
        this.girokontoIban = girokontoIban;
        this.datum = datum;
        this.betrag = betrag;
        this.verwendungszweck = verwendungszweck;
    }

    public Long getId() {
        return id;
    }

    public IBAN getGirokontoIban() {
        return girokontoIban;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public Betrag getBetrag() {
        return betrag;
    }

    public String getVerwendungszweck() {
        return verwendungszweck;
    }

    @Override
    public String toString() {
        return "Kontoauszug{" +
                "id=" + id +
                ", girokontoIban=" + girokontoIban +
                ", datum=" + datum +
                ", betrag=" + betrag.wert() +
                ", verwendungszweck='" + verwendungszweck + '\'' +
                '}';
    }
}