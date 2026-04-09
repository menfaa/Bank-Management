package com.bank.konto.execptions;

import com.bank.common.IBAN;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Exception, die geworfen wird, wenn zu einer angegebenen IBAN kein Girokonto gefunden wurde.
 * Diese Exception ist eine unchecked Exception (erbt von RuntimeException).
 */
@Schema(description = "Exception, wenn zu einer angegebenen IBAN kein Girokonto gefunden wurde")
public class NoGirokontoFoundException extends RuntimeException {
    @Schema(description = "Die IBAN, zu der kein Girokonto gefunden wurde", example = "DE12345678901234567890")
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