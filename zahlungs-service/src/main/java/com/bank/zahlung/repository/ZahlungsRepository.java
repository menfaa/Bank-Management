package com.bank.zahlung.repository;

import com.bank.zahlung.domain.Zahlung;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZahlungsRepository extends CrudRepository<Zahlung, String> {
    // Du kannst hier eigene Query-Methoden ergänzen, z.B.:
    // List<Zahlung> findByBetragGreaterThan(BigDecimal betrag);
}