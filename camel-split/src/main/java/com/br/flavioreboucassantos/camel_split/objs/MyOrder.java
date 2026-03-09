package com.br.flavioreboucassantos.camel_split.objs;

import java.util.List;
import java.util.Objects;

public class MyOrder {

	private final String orderId;
	private final List<String> listItemId;

	public MyOrder(final String orderId, final List<String> listItemId) {
		this.orderId = orderId;
		this.listItemId = listItemId;
	}

	public String getOrderId() {
		return orderId;
	}
	

	public List<String> getListItemId() {
		return listItemId;
	}	

	@Override
	public int hashCode() {
		return Objects.hash(listItemId, orderId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyOrder other = (MyOrder) obj;
		return Objects.equals(listItemId, other.listItemId) && Objects.equals(orderId, other.orderId);
	}

	@Override
	public String toString() {
		return "MyOrder [orderId=" + orderId + ", listItemId=" + listItemId + "]";
	}

}
