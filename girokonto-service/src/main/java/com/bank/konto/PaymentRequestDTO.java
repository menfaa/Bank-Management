package com.bank.konto;

import java.time.LocalDate;

import com.bank.common.Betrag;

public class PaymentRequestDTO {
    private Betrag betrag;
    private String verwendungszweck;
    private String kartennummer;
    private String haendler;
    private LocalDate datum;
    private String empfaengerIban;
    private String empfaengerName;

    // Getter/Setter
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