package com.bank.common.events;

import java.io.Serializable;
import java.time.LocalDate;

import com.bank.common.Betrag;

public class PaymentRequestedEvent implements Serializable {
    private String girokontoIban;
    private Betrag betrag;
    private String verwendungszweck;
    private String kartennummer;
    private String haendler;
    private LocalDate datum;
    private String empfaengerIban;
    private String empfaengerName;

    public PaymentRequestedEvent() {
    }

    // Für Karten Zahlung
    public PaymentRequestedEvent(String girokontoIban, Betrag betrag, String verwendungszweck,
            String kartennummer, String haendler, LocalDate datum) {
        this.girokontoIban = girokontoIban;
        this.betrag = betrag;
        this.verwendungszweck = verwendungszweck;
        this.kartennummer = kartennummer;
        this.haendler = haendler;
        this.datum = datum;
    }

    // Für Sepa Zahlung
    public PaymentRequestedEvent(String girokontoIban, Betrag betrag, String verwendungszweck,
            LocalDate datum,
            String empfaengerIban, String empfaengerName) {
        this.girokontoIban = girokontoIban;
        this.betrag = betrag;
        this.verwendungszweck = verwendungszweck;
        this.datum = datum;
        this.empfaengerIban = empfaengerIban;
        this.empfaengerName = empfaengerName;
    }

    public String getGirokontoIban() {
        return girokontoIban;
    }

    public void setGirokontoIban(String girokontoIban) {
        this.girokontoIban = girokontoIban;
    }

    public Betrag getBetrag() {
        return betrag;
    }

    public void setBetrag(Betrag betrag) {
        this.betrag = betrag;
    }

    public String getVerwendungszweck() {
        return verwendungszweck;
    }

    public void setVerwendungszweck(String verwendungszweck) {
        this.verwendungszweck = verwendungszweck;
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

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getEmpfaengerIban() {
        return empfaengerIban;
    }

    public void setEmpfaengerIban(String empfaengerIban) {
        this.empfaengerIban = empfaengerIban;
    }

    public String getEmpfaengerName() {
        return empfaengerName;
    }

    public void setEmpfaengerName(String empfaengerName) {
        this.empfaengerName = empfaengerName;
    }

}