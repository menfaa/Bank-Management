package com.bank.common.events; // Definiert das Package, in dem sich die Klasse befindet

import java.io.Serializable; // Importiert das Interface für Serialisierbarkeit

public class PaymentCompletedEvent implements Serializable { // Die Klasse repräsentiert ein Event und ist serialisierbar
    private String girokontoIban; // IBAN des Girokontos, von dem die Zahlung ausging
    private String zahlungId; // Eindeutige ID der Zahlung
    private String zahlungsart; // Art der Zahlung (z.B. Überweisung, Lastschrift)
    private String verwendungszweck; // Verwendungszweck der Zahlung
    private double betrag; // Betrag der Zahlung

    public PaymentCompletedEvent() { // Standardkonstruktor (wird z.B. von Serialisierern benötigt)
    }

    public PaymentCompletedEvent(String girokontoIban, String zahlungId, String zahlungsart, String verwendungszweck,
            double betrag) { // Konstruktor zum Setzen aller Felder
        this.girokontoIban = girokontoIban;
        this.zahlungId = zahlungId;
        this.zahlungsart = zahlungsart;
        this.verwendungszweck = verwendungszweck;
        this.betrag = betrag;
    }

    public String getGirokontoIban() { // Getter für girokontoIban
        return girokontoIban;
    }

    public void setGirokontoIban(String girokontoIban) { // Setter für girokontoIban
        this.girokontoIban = girokontoIban;
    }

    public String getZahlungId() { // Getter für zahlungId
        return zahlungId;
    }

    public void setZahlungId(String zahlungId) { // Setter für zahlungId
        this.zahlungId = zahlungId;
    }

    public String getZahlungsart() { // Getter für zahlungsart
        return zahlungsart;
    }

    public void setZahlungsart(String zahlungsart) { // Setter für zahlungsart
        this.zahlungsart = zahlungsart;
    }

    public String getVerwendungszweck() { // Getter für verwendungszweck
        return verwendungszweck;
    }

    public void setVerwendungszweck(String verwendungszweck) { // Setter für verwendungszweck
        this.verwendungszweck = verwendungszweck;
    }

    public double getBetrag() { // Getter für betrag
        return betrag;
    }

    public void setBetrag(double betrag) { // Setter für betrag
        this.betrag = betrag;
    }

}