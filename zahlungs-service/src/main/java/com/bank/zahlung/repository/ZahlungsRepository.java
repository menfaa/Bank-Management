package com.bank.zahlung.repository;

import com.bank.zahlung.domain.Zahlung;

import org.springframework.data.repository.CrudRepository; // Basis-Repository für CRUD-Operationen
import org.springframework.stereotype.Repository; // Markiert das Interface als Repository-Komponente

/**
 * Repository-Interface für Zahlungen.
 * Bietet CRUD-Operationen (Create, Read, Update, Delete) für Zahlung-Entities.
 * Die ID ist ein String (UUID).
 */
@Repository // Spring: Markiert dieses Interface als Repository-Komponente
public interface ZahlungsRepository extends CrudRepository<Zahlung, String> {
    // Du kannst hier eigene Query-Methoden ergänzen, z.B.:
    // List<Zahlung> findByBetragGreaterThan(BigDecimal betrag);
}