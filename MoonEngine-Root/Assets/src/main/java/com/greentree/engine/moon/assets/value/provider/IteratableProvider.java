package com.greentree.engine.moon.assets.value.provider;

import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.merge.MIValue;

public final class IteratableProvider<T> implements ValueProvider<Value<Iterable<T>>> {
	
	public static final int CHARACTERISTICS = NOT_NULL;
	
	private final ValueProvider<? extends Iterable<? extends Value<? extends T>>> providers;
	
	private IteratableProvider(ValueProvider<? extends Iterable<? extends Value<? extends T>>> providers) {
		this.providers = providers;
	}
	
	public static <T> ValueProvider<Value<Iterable<T>>> newProvider(
			Value<? extends Iterable<? extends Value<? extends T>>> values) {
		return newProvider(values.openProvider());
	}
	
	public static <T> ValueProvider<Value<Iterable<T>>> newProvider(
			ValueProvider<? extends Iterable<? extends Value<? extends T>>> providers) {
		return new IteratableProvider<>(providers);
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public void close() {
		providers.close();
	}
	
	@Override
	public Value<Iterable<T>> get() {
		return new MIValue<>(providers.get());
	}
	
	@Override
	public boolean isChenge() {
		return providers.isChenge();
	}
	
	@Override
	public ValueProvider<Value<Iterable<T>>> copy() {
		return newProvider(providers);
	}
	
}
