package com.bank.kunde.repository;

import org.springframework.data.repository.CrudRepository;

import com.bank.kunde.domain.Inhaber;
import com.bank.common.InhaberID; //InhaberID

// Repository-Interface für Girokonto

public interface InhaberRepository extends CrudRepository<Inhaber, InhaberID> {

}