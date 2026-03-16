package com.bank.zahlung.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.bank.common.Betrag;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "zahlungsart")
public abstract class Zahlung {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Betrag betrag;
    private LocalDate datum;
    private String verwendungszweck;

    protected Zahlung() {
    }

    protected Zahlung(Betrag betrag, LocalDate datum, String verwendungszweck, Zahlungsart zahlungsart) {
        this.betrag = betrag;
        this.datum = datum;
        this.verwendungszweck = verwendungszweck;
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