package com.greentree.engine.moon.assets.value.provider;

import java.io.ObjectStreamException;

public final class NullProvider<T> implements ValueProvider<T> {
	
	
	private static final NullProvider<?> INSTANCE = new NullProvider<>();
	
	public static final int CHARACTERISTICS = CONST | BLANCK_CLOSE | DISTINCT_CHANGE;
	
	private NullProvider() {
	}
	
	@SuppressWarnings("unchecked")
	public static <T> NullProvider<T> instance() {
		return (NullProvider<T>) INSTANCE;
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public void close() {
	}
	
	@Override
	public ValueProvider<T> copy() {
		return this;
	}
	
	@Override
	public T get() {
		return null;
	}
	
	
	@Override
	public boolean isChenge() {
		return false;
	}
	
	private Object readResolve() throws ObjectStreamException {
		return INSTANCE;
	}
}
