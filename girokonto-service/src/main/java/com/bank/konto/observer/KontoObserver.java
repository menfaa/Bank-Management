package com.bank.konto.observer;

import com.bank.konto.domain.Girokonto;
import com.bank.konto.domain.Kontoauszug;

public interface KontoObserver {
    void update(Girokonto konto, Kontoauszug auszug);
}