package com.br.flavioreboucassantos.camel_xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmlItem {

	@XmlAttribute
	String id;

	@XmlValue
	String quantity;

	@Override
	public String toString() {
		return "XmlItem [id=" + id + ", quantity=" + quantity + "]";
	}

}
