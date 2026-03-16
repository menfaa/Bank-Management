// Bounded Context: Inhaber
package com.bank.kunde.domain;

import jakarta.persistence.*;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Persistable;

import com.bank.common.InhaberID; //InhaberID

@AggregateRoot
@Entity
public class Inhaber implements Persistable<InhaberID> {

    @Id
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "inhaber_id")) // EXPLIZIT MAPEN
    @Identity
    private InhaberID inhaberId; // InhaberID als Value Object und Identität

    @Column(nullable = false)
    private String name; // Name des Inhabers

    @Transient // Wird nicht in der Datenbank gespeichert
    private boolean isNew = true;

    protected Inhaber() {
    }

    public Inhaber(InhaberID inhaberId, String name) {
        this.inhaberId = inhaberId;
        this.name = name;
    }

    public InhaberID getInhaberId() {
        return inhaberId;
    }

    public String getName() {
        return name;
    }

    @Override
    public @Nullable InhaberID getId() {
        return inhaberId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PrePersist
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public String toString() {
        return "Inhaber{" +
                "inhaberId=" + inhaberId +
                ", name='" + name + '\'' +
                '}';
    }

}