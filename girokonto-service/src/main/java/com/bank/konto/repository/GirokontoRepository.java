package com.bank.konto.repository;

import org.springframework.data.jpa.repository.Query; // Ermöglicht benutzerdefinierte JPQL-Abfragen
import org.springframework.data.repository.CrudRepository; // Basis-Repository für CRUD-Operationen
import org.springframework.data.repository.query.Param; // Ermöglicht benannte Parameter in Abfragen

import com.bank.common.IBAN;
import com.bank.common.InhaberID;
import com.bank.konto.domain.Girokonto;

import java.util.Optional;

/**
 * Repository-Interface für Girokonto.
 * Bietet CRUD-Operationen und spezielle Abfragen für Girokonten.
 */
public interface GirokontoRepository extends CrudRepository<Girokonto, IBAN> {

    /**
     * Findet ein Girokonto anhand der IBAN und lädt die Kontoauszüge direkt mit (Eager Fetch).
     * @param iban IBAN als String
     * @return Optional mit Girokonto und Kontoauszügen
     */
    @Query("SELECT g FROM Girokonto g LEFT JOIN FETCH g.kontoauszuege WHERE g.iban.value = :iban") // JPQL-Abfrage mit Eager Fetch
    Optional<Girokonto> findByIbanWithKontoauszuege(@Param("iban") String iban); // @Param: Bindet den Parameter an die Query

    /**
     * Findet ein Girokonto anhand der IBAN.
     * @param iban IBAN-Objekt
     * @return Optional mit Girokonto
     */
    Optional<Girokonto> findByIban(IBAN iban);

    /**
     * Löscht ein Girokonto anhand der IBAN.
     * @param iban IBAN-Objekt
     */
    void deleteByIban(IBAN iban);

    /**
     * Löscht alle Girokonten eines bestimmten Inhabers.
     * @param inhaberId InhaberID-Objekt
     */
    void deleteAllByInhaberId(InhaberID inhaberId);
}