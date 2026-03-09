package com.br.flavioreboucassantos.camel_split.objs;

import java.util.List;
import java.util.Objects;

public class CustomerOrders {

	private final String customerId;
	private final List<MyOrder> listOrder;

	public CustomerOrders(final String customerId, final List<MyOrder> listOrder) {
		this.customerId = customerId;
		this.listOrder = listOrder;
	}

	public String getCustomerId() {
		return customerId;
	}

	public List<MyOrder> getListOrder() {
		return listOrder;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId, listOrder);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerOrders other = (CustomerOrders) obj;
		return Objects.equals(customerId, other.customerId) && Objects.equals(listOrder, other.listOrder);
	}

	@Override
	public String toString() {
		return "CustomerOrders [customerId=" + customerId + ", listOrder=" + listOrder + "]";
	}

}
