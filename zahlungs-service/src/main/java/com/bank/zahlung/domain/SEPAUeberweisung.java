package com.bank.zahlung.domain;

import jakarta.persistence.Column; // JPA: Für Spaltenzuordnung
import jakarta.persistence.DiscriminatorValue; // JPA: Diskriminatorwert für Vererbung
import jakarta.persistence.Entity; // JPA: Markiert die Klasse als Entity
import java.time.LocalDate;

import com.bank.common.Betrag;

/**
 * Entity für eine SEPA-Überweisung.
 * Erbt von Zahlung und speichert zusätzliche Felder wie Auftraggeber- und Empfänger-IBAN sowie Empfängername.
 */
@Entity // JPA: Markiert die Klasse als persistierbare Entity (Tabelle in der Datenbank)
@DiscriminatorValue("SEPA") // JPA: Diskriminatorwert für Vererbung (Single Table Inheritance)
public class SEPAUeberweisung extends Zahlung {

    @Column(nullable = true) // JPA: Spalte darf null sein (optional)
    private String auftraggeberIban; // IBAN des Auftraggebers

    @Column(nullable = true) // JPA: Spalte darf null sein (optional)
    private String empfaengerIban; // IBAN des Empfängers

    @Column(nullable = true) // JPA: Spalte darf null sein (optional)
    private String empfaengerName; // Name des Empfängers

    /**
     * Standardkonstruktor für Hibernate.
     */
    public SEPAUeberweisung() {
        super();
    }

    /**
     * Konstruktor für eine neue SEPA-Überweisung.
     */
    public SEPAUeberweisung(String auftraggeberIban, String empfaengerIban, String empfaengerName,
            Betrag betrag, LocalDate datum, String verwendungszweck) {
        super(betrag, datum, verwendungszweck, Zahlungsart.SEPA);
        this.auftraggeberIban = auftraggeberIban;
        this.empfaengerIban = empfaengerIban;
        this.empfaengerName = empfaengerName;
    }

    public String getAuftraggeberIban() {
        return auftraggeberIban;
    }

    public void setAuftraggeberIban(String auftraggeberIban) {
        this.auftraggeberIban = auftraggeberIban;
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

    @Override
    public String toString() {
        return "SEPAUeberweisung{" +
                "id=" + getId() +
                ", auftraggeberIban='" + auftraggeberIban + '\'' +
                ", empfaengerIban='" + empfaengerIban + '\'' +
                ", empfaengerName='" + empfaengerName + '\'' +
                ", betrag=" + getBetrag() +
                ", datum=" + getDatum() +
                ", verwendungszweck='" + getVerwendungszweck() + '\'' +
                '}';
    }
}