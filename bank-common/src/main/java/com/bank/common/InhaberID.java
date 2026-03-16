package com.bank.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

import org.jmolecules.ddd.annotation.ValueObject;

// Value Object für InhaberID
@ValueObject
@Embeddable
public class InhaberID implements Serializable {

    @Column(name = "inhaber_id")
    private String value;

    protected InhaberID() {
    }

    @JsonCreator
    public InhaberID(String value) {
        this.value = value;
    }

    @JsonValue
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
        InhaberID inhaberID = (InhaberID) o;
        return Objects.equals(value, inhaberID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}