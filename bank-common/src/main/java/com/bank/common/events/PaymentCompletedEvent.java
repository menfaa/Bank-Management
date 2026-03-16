package com.bank.common.events;

import java.io.Serializable;

public class PaymentCompletedEvent implements Serializable {
    private String girokontoIban;
    private String zahlungId;
    private String zahlungsart;
    private String verwendungszweck;
    private double betrag;

    public PaymentCompletedEvent() {
    }

    public PaymentCompletedEvent(String girokontoIban, String zahlungId, String zahlungsart, String verwendungszweck,
            double betrag) {
        this.girokontoIban = girokontoIban;
        this.zahlungId = zahlungId;
        this.zahlungsart = zahlungsart;
        this.verwendungszweck = verwendungszweck;
        this.betrag = betrag;
    }

    public String getGirokontoIban() {
        return girokontoIban;
    }

    public void setGirokontoIban(String girokontoIban) {
        this.girokontoIban = girokontoIban;
    }

    public String getZahlungId() {
        return zahlungId;
    }

    public void setZahlungId(String zahlungId) {
        this.zahlungId = zahlungId;
    }

    public String getZahlungsart() {
        return zahlungsart;
    }

    public void setZahlungsart(String zahlungsart) {
        this.zahlungsart = zahlungsart;
    }

    public String getVerwendungszweck() {
        return verwendungszweck;
    }

    public void setVerwendungszweck(String verwendungszweck) {
        this.verwendungszweck = verwendungszweck;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

}