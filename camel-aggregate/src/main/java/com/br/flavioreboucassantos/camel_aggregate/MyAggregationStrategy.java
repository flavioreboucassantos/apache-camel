package com.br.flavioreboucassantos.camel_aggregate;

import java.util.Objects;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class MyAggregationStrategy implements AggregationStrategy {

	@Override
	public Exchange aggregate(final Exchange oldExchange, final Exchange newExchange) {
		if (Objects.isNull(oldExchange))
			return newExchange;

		final String oldBody = oldExchange.getIn().getBody(String.class);
		final String newBody = newExchange.getIn().getBody(String.class);

		final String aggregatedBody = oldBody + " >STRAGEGY> " + newBody;

		oldExchange.getIn().setBody(aggregatedBody);

		return oldExchange;
	}

}
