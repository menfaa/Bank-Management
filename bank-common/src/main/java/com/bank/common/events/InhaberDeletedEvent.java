package com.bank.common.events;

import java.io.Serializable;

import com.bank.common.InhaberID;

public class InhaberDeletedEvent implements Serializable {
    private InhaberID inhaberId;

    public InhaberDeletedEvent() {
    }

    public InhaberDeletedEvent(InhaberID inhaberId) {
        this.inhaberId = inhaberId;
    }

    public InhaberID getInhaberId() {
        return inhaberId;
    }

    public void setInhaberId(InhaberID inhaberId) {
        this.inhaberId = inhaberId;
    }
}