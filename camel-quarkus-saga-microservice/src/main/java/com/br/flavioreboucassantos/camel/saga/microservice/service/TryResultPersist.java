package com.br.flavioreboucassantos.camel.saga.microservice.service;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.PersistenceException;

/**
 * @param <E>
 * @author Flávio Rebouças Santos - flavioReboucasSantos@gmail.com
 */
public final record TryResultPersist<E extends PanacheEntityBase>(
		PersistenceException persistenceException,
		E persistedEntity) {
}
