package com.br.flavioreboucassantos.camel_xml;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmlListItems {
	/*
	 * CLASSE OPCIONAL
	 */
	@XmlElement(name = "item")
	List<XmlItem> listXmlItem = new ArrayList<XmlItem>();
}
