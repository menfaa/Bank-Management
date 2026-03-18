package com.bank.konto;

import java.time.LocalDate;

import com.bank.common.Betrag;

/**
 * Data Transfer Object (DTO) für Zahlungsanfragen.
 * Enthält alle notwendigen Informationen für eine Zahlungsanforderung,
 * z.B. Betrag, Verwendungszweck, Kartendaten und Empfängerdaten.
 */
public class PaymentRequestDTO {
    private Betrag betrag; // Der zu zahlende Betrag
    private String verwendungszweck; // Verwendungszweck der Zahlung
    private String kartennummer; // Kartennummer (bei Kartenzahlung)
    private String haendler; // Händler (bei Kartenzahlung)
    private LocalDate datum; // Datum der Zahlung
    private String empfaengerIban; // IBAN des Empfängers (bei Überweisung)
    private String empfaengerName; // Name des Empfängers (bei Überweisung)

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