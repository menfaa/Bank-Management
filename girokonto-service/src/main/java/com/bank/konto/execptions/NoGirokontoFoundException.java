package com.bank.konto.execptions;

import com.bank.common.IBAN;

public class NoGirokontoFoundException extends RuntimeException {
    IBAN iban;

    public NoGirokontoFoundException(IBAN iban) {
        this.iban = iban;
    }

    public IBAN getIban() {
        return iban;
    }

}
