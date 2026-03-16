package com.bank.kunde.execptions;

import com.bank.common.InhaberID; //InhaberID

public class NoInhaberFoundException extends RuntimeException {
    InhaberID inhaberID;

    public NoInhaberFoundException(InhaberID inhaberID) {
        this.inhaberID = inhaberID;
    }

    public InhaberID getInhaberID() {
        return inhaberID;
    }

}
