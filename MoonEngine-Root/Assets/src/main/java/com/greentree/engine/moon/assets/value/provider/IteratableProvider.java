package com.greentree.engine.moon.assets.value.provider;

import com.greentree.engine.moon.assets.value.Value;

public class IteratableProvider<T> implements ValueProvider<Value<Iterable<T>>> {
	
	public static final int CHARACTERISTICS = NOT_NULL;
	
	private final ValueProvider<Iterable<ValueProvider<T>>> providers;
	
	private IteratableProvider(ValueProvider<Iterable<ValueProvider<T>>> providers) {
		this.providers = providers;
	}
	
	public static <T> ValueProvider<Value<Iterable<T>>> newValue(Value<Iterable<Value<T>>> values) {
		return null;
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
		return null;
	}
	
	@Override
	public boolean isChenge() {
		if(providers.isChenge())
			return true;
		final var ps = providers.get();
		for(var p : ps)
			if(p.isChenge())
				return true;
		return true;
	}
	
}
