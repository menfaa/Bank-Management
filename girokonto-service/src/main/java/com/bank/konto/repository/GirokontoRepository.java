package com.bank.konto.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bank.common.IBAN;
import com.bank.common.InhaberID;
import com.bank.konto.domain.Girokonto;

import java.util.Optional;

// Repository-Interface für Girokonto

public interface GirokontoRepository extends CrudRepository<Girokonto, IBAN> {

    @Query("SELECT g FROM Girokonto g LEFT JOIN FETCH g.kontoauszuege WHERE g.iban.value = :iban")
    Optional<Girokonto> findByIbanWithKontoauszuege(@Param("iban") String iban);

    Optional<Girokonto> findByIban(IBAN iban);

    void deleteByIban(IBAN iban);

    void deleteAllByInhaberId(InhaberID inhaberId);
}