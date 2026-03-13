package com.br.flavioreboucassantos.camel.saga.microservice.service;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * 
 * @param <E>
 * @author Flávio Rebouças Santos - flavioReboucasSantos@gmail.com
 */
public final record TryResultUpdate<E extends PanacheEntityBase>(
		boolean notFound,
		Exception exception,
		E updatedEntity) {
}
