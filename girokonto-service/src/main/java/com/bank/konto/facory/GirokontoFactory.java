package com.bank.konto.facory;

import org.springframework.stereotype.Service;

import com.bank.konto.domain.Girokonto;
import com.bank.common.IBAN;
import com.bank.common.InhaberID;

// Factory für die Erstellung von Girokonto-Aggregaten
@Service
public class GirokontoFactory {

    public Girokonto create(String ibanValue, InhaberID inhaberId) {
        IBAN iban = new IBAN(ibanValue);
        return new Girokonto(iban, inhaberId);
    }
}