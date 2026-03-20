package com.br.flavioreboucassantos.camel_xml;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlOrder {

	@XmlAttribute
	String id;

	@XmlElement(name = "items")
	XmlListItems items;

	@XmlElement(name = "payment")
	XmlPayment payment;
}
