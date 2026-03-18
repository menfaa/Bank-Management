package com.bank.kunde.repository;

import org.springframework.data.repository.CrudRepository; // Basis-Repository für CRUD-Operationen

import com.bank.kunde.domain.Inhaber;
import com.bank.common.InhaberID; // Value Object für InhaberID

/**
 * Repository-Interface für Inhaber.
 * Bietet CRUD-Operationen (Create, Read, Update, Delete) für Inhaber-Entities.
 * Die ID ist ein Value Object vom Typ InhaberID.
 */
public interface InhaberRepository extends CrudRepository<Inhaber, InhaberID> {
    // Zusätzliche Abfragen können hier als Methoden ergänzt werden (z.B. findByName)
}