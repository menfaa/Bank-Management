package com.bank.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

import org.jmolecules.ddd.annotation.ValueObject;

// Value Object für IBAN
@ValueObject
@Embeddable
public class IBAN {

    @Column(name = "iban")
    private String value;

    protected IBAN() {
    }

    public IBAN(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        IBAN iban = (IBAN) o;
        return Objects.equals(value, iban.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}