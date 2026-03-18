package com.bank.konto.execptions;

import com.bank.common.IBAN;

/**
 * Exception, die geworfen wird, wenn zu einer angegebenen IBAN kein Girokonto gefunden wurde.
 * Diese Exception ist eine unchecked Exception (erbt von RuntimeException).
 */
public class NoGirokontoFoundException extends RuntimeException {
    IBAN iban; // Die IBAN, zu der kein Girokonto gefunden wurde

    /**
     * Konstruktor, der die nicht gefundene IBAN entgegennimmt.
     */
    public NoGirokontoFoundException(IBAN iban) {
        this.iban = iban;
    }

    /**
     * Gibt die IBAN zurück, zu der kein Girokonto gefunden wurde.
     */
    public IBAN getIban() {
        return iban;
    }

}