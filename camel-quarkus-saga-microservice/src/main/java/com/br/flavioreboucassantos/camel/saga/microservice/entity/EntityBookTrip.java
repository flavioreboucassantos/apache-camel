package com.br.flavioreboucassantos.camel.saga.microservice.entity;

import java.util.Objects;

import com.br.flavioreboucassantos.camel.saga.microservice.dto.DtoBookTrip;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Flávio Rebouças Santos - flavioReboucasSantos@gmail.com
 */
@Entity
@Table(name = "book_trip")
public class EntityBookTrip extends PanacheEntityBase {

	@Id
	@Column(name = "id_book_trip")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long idBookTrip;

	@Column(name = "id_book_hotel_service")
	public long idBookHotelService;

	@Column(name = "id_book_flight-service")
	public long idBookFlightService;

	public EntityBookTrip() {
	}

	public EntityBookTrip(final DtoBookTrip dtoBookTrip) {
		idBookHotelService = dtoBookTrip.idBookHotelService();
		idBookFlightService = dtoBookTrip.idBookFlightService();
	}

	@Override
	public int hashCode() {
		return Objects.hash(idBookFlightService, idBookHotelService, idBookTrip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityBookTrip other = (EntityBookTrip) obj;
		return idBookFlightService == other.idBookFlightService && idBookHotelService == other.idBookHotelService && idBookTrip == other.idBookTrip;
	}

}
