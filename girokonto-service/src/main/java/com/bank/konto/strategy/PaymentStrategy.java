package com.bank.konto.strategy;

import com.bank.common.Betrag;
import com.bank.konto.domain.Girokonto;

public interface PaymentStrategy {
    void executePayment(Girokonto girokonto, Betrag betrag, String verwendungszweck);
}
