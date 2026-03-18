package com.bank.zahlung.domain;

import jakarta.persistence.Column; // JPA: Für Spaltenzuordnung
import jakarta.persistence.DiscriminatorValue; // JPA: Diskriminatorwert für Vererbung
import jakarta.persistence.Entity; // JPA: Markiert die Klasse als Entity
import java.time.LocalDate;

import com.bank.common.Betrag;

/**
 * Entity für eine Kartenzahlung.
 * Erbt von Zahlung und speichert zusätzliche Felder wie Kartennummer und Händler.
 */
@Entity // JPA: Markiert die Klasse als persistierbare Entity (Tabelle in der Datenbank)
@DiscriminatorValue("KARTE") // JPA: Diskriminatorwert für Vererbung (Single Table Inheritance)
public class Kartenzahlung extends Zahlung {

    @Column(nullable = false) // JPA: Spalte darf nicht null sein
    private String girokontoIban; // IBAN des zugehörigen Girokontos

    @Column(nullable = true) // JPA: Spalte darf null sein (optional)
    private String kartennummer; // Kartennummer (optional)

    @Column(nullable = true) // JPA: Spalte darf null sein (optional)
    private String haendler; // Händler (optional)

    /**
     * Standardkonstruktor für Hibernate (geschützt).
     */
    protected Kartenzahlung() {
        super();
    }

    /**
     * Konstruktor für eine neue Kartenzahlung.
     */
    public Kartenzahlung(String girokontoIban, LocalDate datum, Betrag betrag, String verwendungszweck,
            String kartennummer, String haendler) {
        super(betrag, datum, verwendungszweck, Zahlungsart.KARTE);
        this.girokontoIban = girokontoIban;
        this.kartennummer = kartennummer;
        this.haendler = haendler;
    }

    public String getKartennummer() {
        return kartennummer;
    }

    public void setKartennummer(String kartennummer) {
        this.kartennummer = kartennummer;
    }

    public String getHaendler() {
        return haendler;
    }

    public void setHaendler(String haendler) {
        this.haendler = haendler;
    }

    @Override
    public String toString() {
        return "Kartenzahlung{" +
                "id=" + getId() +
                ", betrag=" + getBetrag() +
                ", datum=" + getDatum() +
                ", verwendungszweck='" + getVerwendungszweck() + '\'' +
                ", kartennummer='" + kartennummer + '\'' +
                ", haendler='" + haendler + '\'' +
                '}';
    }

    public String getGirokontoIban() {
        return girokontoIban;
    }

    public void setGirokontoIban(String girokontoIban) {
        this.girokontoIban = girokontoIban;
    }
}