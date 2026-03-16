package com.bank.konto.domain;

import jakarta.persistence.Embeddable;
import org.jmolecules.ddd.annotation.ValueObject;

import com.bank.common.Betrag;

@ValueObject // DDD-Annotation für Value Object
@Embeddable // JPA-Annotation für eingebettetes Objekt
public record Kontostand(Betrag betrag) {
    // Der Kontostand besteht nur aus einem Wert (Betrag)
    // Mit einem Record bekommst du automatisch equals, hashCode und toString
}