package com.bank.zahlung.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDate;

import com.bank.common.Betrag;

@Entity
@DiscriminatorValue("KARTE")
public class Kartenzahlung extends Zahlung {

    @Column(nullable = false)
    private String girokontoIban;

    @Column(nullable = true)
    private String kartennummer;

    @Column(nullable = true)
    private String haendler;

    // Der von Hibernate benötigte Standardkonstruktor
    protected Kartenzahlung() {
        super();
    }

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