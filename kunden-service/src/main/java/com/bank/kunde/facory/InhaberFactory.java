package com.bank.kunde.facory;

import com.bank.kunde.domain.Inhaber;

import org.springframework.stereotype.Service;

import com.bank.common.InhaberID; //InhaberID

// Factory für die Erstellung von Inhaber-Aggregaten
@Service
public class InhaberFactory {

    public Inhaber create(String inhaberIdValue, String name) {
        InhaberID inhaberId = new InhaberID(inhaberIdValue);
        return new Inhaber(inhaberId, name);
    }
}