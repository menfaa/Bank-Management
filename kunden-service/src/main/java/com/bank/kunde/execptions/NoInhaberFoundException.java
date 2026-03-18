package com.bank.kunde.execptions;

import com.bank.common.InhaberID; // Importiert das Value Object InhaberID

/**
 * Exception, die geworfen wird, wenn zu einer angegebenen InhaberID kein Inhaber gefunden wurde.
 * Diese Exception ist eine unchecked Exception (erbt von RuntimeException).
 */
public class NoInhaberFoundException extends RuntimeException {
    InhaberID inhaberID; // Die InhaberID, zu der kein Inhaber gefunden wurde

    /**
     * Konstruktor, der die nicht gefundene InhaberID entgegennimmt.
     */
    public NoInhaberFoundException(InhaberID inhaberID) {
        this.inhaberID = inhaberID;
    }

    /**
     * Gibt die InhaberID zurück, zu der kein Inhaber gefunden wurde.
     */
    public InhaberID getInhaberID() {
        return inhaberID;
    }

}