package com.bank.konto.domain;

import jakarta.persistence.*;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

import com.bank.common.Betrag;
import com.bank.common.IBAN;

import java.time.LocalDate;

@jakarta.persistence.Entity // JPA-Entity
@Entity // DDD-Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Vererbung: alles in einer Tabelle
@DiscriminatorColumn(name = "transaktionsart") // Diskriminatorspalte heißt "transaktionsart"
public class Kontoauszug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Identity
    private Long id; // Eindeutige ID des Kontoauszugs

    @Embedded // IBAN als Value Object (DDD-konform)
    @AttributeOverride(name = "value", column = @Column(name = "girokonto_iban", nullable = false))
    private IBAN girokontoIban; // Fremdschlüssel: IBAN des zugehörigen Girokontos

    @Column(nullable = false)
    private LocalDate datum; // Buchungsdatum

    @Column(nullable = false)
    @Embedded
    // Weil Hibernate mappt das Feld standardmäßig auf die Spalte wert
    // und in SQL-Schema heißt die Spalte aber betrag (und ist NOT NULL).
    @AttributeOverride(name = "wert", column = @Column(name = "betrag"))
    private Betrag betrag; // Betrag (positiv = Einzahlung, negativ = Auszahlung)

    @Column(nullable = false)
    private String verwendungszweck; // Verwendungszweck

    protected Kontoauszug() {
        // Für JPA
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