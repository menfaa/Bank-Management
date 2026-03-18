package com.bank.common.events; // Definiert das Package, in dem sich die Klasse befindet

import java.io.Serializable; // Importiert das Interface für Serialisierbarkeit

import com.bank.common.InhaberID; // Importiert die Klasse InhaberID

public class InhaberDeletedEvent implements Serializable { // Die Klasse repräsentiert ein Event und ist serialisierbar
    private InhaberID inhaberId; // Feld für die gelöschte InhaberID

    public InhaberDeletedEvent() { // Standardkonstruktor (wird z.B. von Serialisierern benötigt)
    }

    public InhaberDeletedEvent(InhaberID inhaberId) { // Konstruktor zum Setzen der InhaberID
        this.inhaberId = inhaberId;
    }

    public InhaberID getInhaberId() { // Getter für inhaberId
        return inhaberId;
    }

    public void setInhaberId(InhaberID inhaberId) { // Setter für inhaberId
        this.inhaberId = inhaberId;
    }
}