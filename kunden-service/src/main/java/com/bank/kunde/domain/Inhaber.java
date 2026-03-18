// Bounded Context: Inhaber
package com.bank.kunde.domain;

import jakarta.persistence.*; // JPA-Annotationen
import org.jmolecules.ddd.annotation.AggregateRoot; // DDD: Markiert die Klasse als Aggregate Root
import org.jmolecules.ddd.annotation.Identity; // DDD: Markiert das Identitätsfeld
import org.jspecify.annotations.Nullable; // Für Nullable-Rückgabewerte
import org.springframework.data.domain.Persistable; // Für Spring Data Persistable-Interface

import com.bank.common.InhaberID; // Value Object für InhaberID

/**
 * Die Klasse Inhaber repräsentiert einen Kontoinhaber als Aggregate Root im DDD-Kontext.
 * Sie ist eine JPA-Entity und speichert die InhaberID und den Namen.
 */
@AggregateRoot // DDD: Markiert diese Klasse als Aggregate Root
@Entity // JPA: Markiert diese Klasse als persistierbare Entity (Tabelle in der Datenbank)
public class Inhaber implements Persistable<InhaberID> {

    @Id // JPA: Primärschlüssel der Tabelle
    @Embedded // JPA: Das Feld ist ein eingebettetes Value Object (InhaberID)
    @AttributeOverride(name = "value", column = @Column(name = "inhaber_id")) // JPA: Überschreibt den Spaltennamen für das Feld "value" in InhaberID zu "inhaber_id"
    @Identity // DDD: Markiert dieses Feld als Identität des Aggregates
    private InhaberID inhaberId; // InhaberID als Value Object und Identität

    @Column(nullable = false) // JPA: Spalte darf nicht null sein
    private String name; // Name des Inhabers

    @Transient // JPA: Wird nicht in der Datenbank gespeichert
    private boolean isNew = true; // Kennzeichnet, ob das Objekt neu ist (für Persistable)

    protected Inhaber() {
        // Für JPA (Standardkonstruktor)
    }

    /**
     * Konstruktor für einen neuen Inhaber.
     */
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

    /**
     * Setzt das Flag isNew auf false, wenn das Objekt geladen oder gespeichert wird.
     */
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