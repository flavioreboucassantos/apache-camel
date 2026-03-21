package com.br.flavioreboucassantos.camel_json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JSONEmployee {

	@JsonProperty
	String name;

	@JsonProperty
	String type;

	@JsonProperty
	String address;

	@JsonProperty
	String city;

	@Override
	public String toString() {
		return "JSONEmployee [name=" + name + ", type=" + type + ", address=" + address + ", city=" + city + "]";
	}

}
