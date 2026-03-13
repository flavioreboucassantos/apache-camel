package com.br.flavioreboucassantos.camel.saga.microservice.entity;

import java.util.Objects;

import com.br.flavioreboucassantos.camel.saga.microservice.dto.DtoBookHotelService;

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
@Table(name = "book_hotel_service")
public class EntityBookHotelService extends PanacheEntityBase {

	@Id
	@Column(name = "id_book_hotel_service")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long idBookHotelService;

	@Column(name = "json_book_hotel_service")
	public String jsonBookHotelService;

	public EntityBookHotelService() {
	}

	public EntityBookHotelService(final DtoBookHotelService dtoBookHotelService) {
		idBookHotelService = dtoBookHotelService.idBookHotelService();
		jsonBookHotelService = dtoBookHotelService.jsonBookHotelService();
	}

	@Override
	public int hashCode() {
		return Objects.hash(idBookHotelService, jsonBookHotelService);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityBookHotelService other = (EntityBookHotelService) obj;
		return idBookHotelService == other.idBookHotelService && Objects.equals(jsonBookHotelService, other.jsonBookHotelService);
	}

}
