package com.br.flavioreboucassantos.camel_xml;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class MyNormalizedStringAdapter extends XmlAdapter<String, String> {

	@Override
	public String marshal(String text) throws Exception {
		return (text == null) ? null : text.trim();
	}

	@Override
	public String unmarshal(String v) throws Exception {
		return (v == null) ? null : v.trim();
	}
}
