package com.bank.konto.facory;

import org.springframework.stereotype.Service; // Spring: Markiert die Klasse als Service-Komponente

import com.bank.konto.domain.Girokonto;
import com.bank.common.IBAN;
import com.bank.common.InhaberID;

/**
 * Factory für die Erstellung von Girokonto-Aggregaten.
 * Diese Klasse kapselt die Erzeugungslogik für Girokonten und wird von Spring als Service verwaltet.
 */
@Service // Spring: Markiert diese Klasse als Service, sodass sie von Spring verwaltet und injiziert werden kann
public class GirokontoFactory {

    /**
     * Erstellt ein neues Girokonto mit der angegebenen IBAN (als String) und InhaberID.
     * @param ibanValue IBAN als String
     * @param inhaberId InhaberID-Objekt
     * @return ein neues Girokonto-Objekt
     */
    public Girokonto create(String ibanValue, InhaberID inhaberId) {
        IBAN iban = new IBAN(ibanValue); // Erstellt ein IBAN-Objekt aus dem String
        return new Girokonto(iban, inhaberId); // Gibt ein neues Girokonto zurück
    }
}