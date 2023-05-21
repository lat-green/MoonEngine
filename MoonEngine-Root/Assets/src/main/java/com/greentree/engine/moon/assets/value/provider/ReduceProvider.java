package com.greentree.engine.moon.assets.value.provider;

import com.greentree.engine.moon.assets.value.Value;


public final class ReduceProvider<T> implements ValueProvider<T> {
	
	public static final int CHARACTERISTICS = 0;
	private final ValueProvider<? extends Value<T>> in;
	private ValueProvider<? extends T> value;
	
	private ReduceProvider(ValueProvider<? extends Value<T>> in) {
		this.in = in;
		final var v = this.in.get();
		if(v != null)
			this.value = v.openProvider();
	}
	
	@Override
	public String toString() {
		return "ReduceProvider [" + in + "]";
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public boolean isChenge() {
		if(value == null)
			return in.isChenge();
		return value.isChenge() || in.isChenge();
	}
	
	@Override
	public T getNotChenge() {
		if(value == null)
			return null;
		return value.getNotChenge();
	}
	
	@Override
	public T get() {
		if(value == null)
			return null;
		if(in.isChenge()) {
			value = this.in.get().openProvider();
		}
		return value.get();
	}
	
	@Override
	public ValueProvider<T> copy() {
		return newProvider(in);
	}
	
	public static <T> ValueProvider<T> newProvider(Value<? extends Value<T>> value) {
		return newProvider(value.openProvider());
	}
	
	public static <T> ValueProvider<T> newProvider(ValueProvider<? extends Value<T>> provider) {
		if(provider.hasCharacteristics(CONST)) {
			final var value = provider.get();
			return value.openProvider();
		}
		return new ReduceProvider<>(provider);
	}
	
}
