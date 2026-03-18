package com.bank.kunde.facory;

import com.bank.kunde.domain.Inhaber;
import org.springframework.stereotype.Service; // Spring: Markiert die Klasse als Service-Komponente
import com.bank.common.InhaberID; // Importiert das Value Object InhaberID

/**
 * Factory für die Erstellung von Inhaber-Aggregaten.
 * Kapselt die Erzeugungslogik für Inhaber und wird von Spring als Service verwaltet.
 */
@Service // Spring: Markiert diese Klasse als Service, sodass sie von Spring verwaltet und injiziert werden kann
public class InhaberFactory {

    /**
     * Erstellt einen neuen Inhaber mit der angegebenen InhaberID (als String) und Namen.
     * @param inhaberIdValue InhaberID als String
     * @param name Name des Inhabers
     * @return ein neues Inhaber-Objekt
     */
    public Inhaber create(String inhaberIdValue, String name) {
        InhaberID inhaberId = new InhaberID(inhaberIdValue); // Erstellt ein InhaberID-Objekt aus dem String
        return new Inhaber(inhaberId, name); // Gibt ein neues Inhaber-Objekt zurück
    }
}