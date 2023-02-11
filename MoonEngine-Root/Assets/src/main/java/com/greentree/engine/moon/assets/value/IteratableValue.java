package com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.provider.IteratableProvider;
import com.greentree.engine.moon.assets.value.provider.ReduceProvider;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

public final class IteratableValue<T> implements Value<Iterable<T>> {
	
	private static final long serialVersionUID = 1L;
	private final Value<? extends Iterable<? extends Value<? extends T>>> values;
	
	public IteratableValue(Value<? extends Iterable<? extends Value<T>>> values) {
		this.values = values;
	}
	
	@Override
	public int characteristics() {
		return IteratableProvider.CHARACTERISTICS;
	}
	
	@Override
	public ValueProvider<Iterable<T>> openProvider() {
		return ReduceProvider.newProvider(IteratableProvider.newProvider(values));
	}
	
	
	
}
