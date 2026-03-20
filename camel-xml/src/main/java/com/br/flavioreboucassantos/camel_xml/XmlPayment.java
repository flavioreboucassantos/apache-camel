package com.br.flavioreboucassantos.camel_xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmlPayment {

	@XmlAttribute
	String type;

	@XmlElement(name = "cardNumber")
	@XmlJavaTypeAdapter(MyNormalizedStringAdapter.class)
	String cardNumber;

	@XmlElement(name = "installments")
	@XmlJavaTypeAdapter(MyNormalizedStringAdapter.class)
	String installments;
}
