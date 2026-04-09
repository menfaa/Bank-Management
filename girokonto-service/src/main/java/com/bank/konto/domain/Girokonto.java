package com.bank.konto.domain; // Paketdeklaration

import jakarta.persistence.*; // Importiert JPA-Annotationen
import org.jmolecules.ddd.annotation.AggregateRoot; // DDD-Annotation für Aggregate Root
import org.jmolecules.ddd.annotation.Identity; // DDD-Annotation für Identity
import java.util.ArrayList; // Für die Initialisierung der Kontoauszugsliste
import java.util.List; // Für die Verwendung von List<Kontoauszug>

import com.bank.common.Betrag; // Importiert das Value Object Betrag
import com.bank.common.IBAN; // Importiert das Value Object IBAN
import com.bank.common.InhaberID; // Importiert das Value Object InhaberID

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Die Klasse Girokonto repräsentiert ein Bankkonto als Aggregate Root im DDD-Kontext.
 * Sie ist eine JPA-Entity und speichert IBAN, InhaberID, Kontostand und Kontoauszüge.
 */
@AggregateRoot // DDD: Markiert diese Klasse als Aggregate Root im Domain-Driven Design
@Entity // JPA: Markiert diese Klasse als persistierbare Entity (Tabelle in der Datenbank)
@Schema(description = "Girokonto-Entity: repräsentiert ein Bankkonto mit IBAN, InhaberID, Kontostand und Kontoauszügen")
public class Girokonto {

    @Id // JPA: Markiert das folgende Feld als Primärschlüssel
    @Embedded // JPA: Das Feld ist ein eingebettetes Value Object (IBAN)
    @AttributeOverride(name = "value", column = @Column(name = "iban")) // JPA: Überschreibt den Spaltennamen für das Feld "value" in IBAN zu "iban"
    @Identity // DDD: Markiert dieses Feld als Identität des Aggregates
     @Schema(description = "IBAN des Kontos", example = "DE12345678901234567890", required = true)
    private IBAN iban; // IBAN als Value Object und Identität

    @Embedded // JPA: Das Feld ist ein eingebettetes Value Object (InhaberID)
    @AttributeOverride(name = "value", column = @Column(name = "inhaber_id")) // JPA: Überschreibt den Spaltennamen für das Feld "value" in InhaberID zu "inhaber_id"
    @Schema(description = "ID des Kontoinhabers", example = "12345", required = true)
    private InhaberID inhaberId; // Referenz auf den KontoInhaber (über dessen ID)

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // JPA: Ein Girokonto hat viele Kontoauszüge, die mit dem Konto gespeichert/gelöscht werden
    @Schema(description = "Liste der Kontoauszüge")
    private List<Kontoauszug> kontoauszuege = new ArrayList<>(); // Liste der Kontoauszüge

    @Embedded // JPA: Das Feld ist ein eingebettetes Value Object (Kontostand)
     @Schema(description = "Aktueller Kontostand")
    private Kontostand kontostand = new Kontostand(new Betrag(0.0)); // Initialer Kontostand ist 0.0

    protected Girokonto() {
        // Geschützter Standardkonstruktor für JPA (wird für das Laden aus der DB benötigt)
    }

    /**
     * Konstruktor für ein neues Girokonto mit IBAN und InhaberID.
     */
    public Girokonto(IBAN iban, InhaberID inhaberId) {
        this.iban = iban; // Setzt die IBAN
        this.inhaberId = inhaberId; // Setzt die InhaberID
    }
    @Schema(description = "IBAN des Kontos")
    public IBAN getIban() {
        return iban; // Gibt die IBAN zurück
    }

    @Schema(description = "ID des Kontoinhabers", example = "12345", required = true)
    public InhaberID getInhaberId() {
        return inhaberId; // Gibt die InhaberID zurück
    }

    public List<Kontoauszug> getKontoauszuege() {
        return kontoauszuege; // Gibt die Liste der Kontoauszüge zurück
    }

    /**
     * Methode zum Hinzufügen eines Kontoauszugs und Aktualisieren des Kontostands.
     */
    public void addKontoauszug(Kontoauszug kontoauszug) {
        kontoauszuege.add(kontoauszug); // Fügt einen Kontoauszug zur Liste hinzu
        // Optional: Kontostand aktualisieren
        Betrag betrag = new Betrag(this.kontostand.betrag().wert() + kontoauszug.getBetrag().wert());
        this.kontostand = new Kontostand(betrag);
    }

    /**
     * Methode zum Entfernen eines Kontoauszugs und Aktualisieren des Kontostands.
     */
    public void removeKontoauszug(Kontoauszug kontoauszug) {
        kontoauszuege.remove(kontoauszug); // Entfernt einen Kontoauszug aus der Liste
        // Optional: Kontostand aktualisieren
        Betrag betrag = new Betrag(this.kontostand.betrag().wert() - kontoauszug.getBetrag().wert());
        this.kontostand = new Kontostand(betrag);
    }

    @Schema(description = "Aktueller Kontostand")
    public Kontostand getKontostand() {
        return kontostand; // Gibt den aktuellen Kontostand zurück
    }

    public void setKontostand(Kontostand kontostand) {
        this.kontostand = kontostand; // Setzt den Kontostand neu
    }

    @Override
    public String toString() {
        return "Girokonto{" +
                "iban=" + iban +
                ", inhaberId='" + inhaberId + '\'' +
                ", kontostand=" + kontostand +
                ", kontoauszuege=" + kontoauszuege +
                '}';
    }
}