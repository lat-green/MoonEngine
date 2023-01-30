package com.greentree.commons.assets.value.provider;

import com.greentree.commons.assets.value.Value;


public final class ReduceProvider<T> implements ValueProvider<T> {
	
	public static final int CHARACTERISTICS = 0;
	private final ValueProvider<? extends Value<? extends T>> in;
	private ValueProvider<? extends T> value;
	
	public ReduceProvider(Value<? extends Value<? extends T>> in) {
		this(in.openProvider());
	}
	
	public ReduceProvider(ValueProvider<? extends Value<? extends T>> in) {
		this.in = in;
		final var v = this.in.get();
		if(v != null)
			this.value = v.openProvider();
	}
	
	@Override
	public int characteristics() {
		if(value == null)
			return CHARACTERISTICS;
		return value.characteristics();
	}
	
	@Override
	public boolean isChenge() {
		if(value == null)
			return in.isChenge();
		return value.isChenge() || in.isChenge();
	}
	
	@Override
	public void close() {
		if(value != null)
			value.close();
		in.close();
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
			value.close();
			value = this.in.get().openProvider();
		}
		return value.get();
	}
	
}
