package com.bank.zahlung.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDate;

import com.bank.common.Betrag;

@Entity
@DiscriminatorValue("SEPA")
public class SEPAUeberweisung extends Zahlung {

    @Column(nullable = true)
    private String auftraggeberIban;

    @Column(nullable = true)
    private String empfaengerIban;

    @Column(nullable = true)
    private String empfaengerName;

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