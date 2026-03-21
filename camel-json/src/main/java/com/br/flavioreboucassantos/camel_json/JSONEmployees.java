package com.br.flavioreboucassantos.camel_json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JSONEmployees {

	@JsonProperty // Mapping Java's personName to JSON's "person_name".
	List<JSONEmployee> employees;

}
