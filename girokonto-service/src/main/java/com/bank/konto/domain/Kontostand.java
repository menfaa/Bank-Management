package com.bank.konto.domain;

import jakarta.persistence.Embeddable; // JPA: Erlaubt das Einbetten dieses Objekts in eine Entity
import org.jmolecules.ddd.annotation.ValueObject; // DDD: Markiert dieses Objekt als Value Object

import com.bank.common.Betrag;

/**
 * Das Value Object Kontostand repräsentiert den aktuellen Stand eines Kontos.
 * Es besteht ausschließlich aus einem Betrag und ist als eingebettbares JPA-Objekt sowie als DDD-ValueObject modelliert.
 */
@ValueObject // DDD: Markiert diese Klasse als Value Object im Domain-Driven Design
@Embeddable // JPA: Erlaubt das Einbetten dieses Objekts in eine Entity
public record Kontostand(Betrag betrag) {
    // Der Kontostand besteht nur aus einem Wert (Betrag)
    // Mit einem Record bekommst du automatisch equals, hashCode und toString
}