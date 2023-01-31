package com.greentree.engine.moon.assets.value.provider;

import java.io.ObjectStreamException;

public final class NullProvider<T> implements ValueProvider<T> {
	
	
	private static final NullProvider<?> INSTANCE = new NullProvider<>();
	
	private Object readResolve() throws ObjectStreamException {
		return INSTANCE;
	}
	
	private NullProvider() {
	}
	
	@SuppressWarnings("unchecked")
	public static <T> NullProvider<T> instance() {
		return (NullProvider<T>) INSTANCE;
	}
	
	public static final int CHARACTERISTICS = CONST | BLANCK_CLOSE | DISTINCT_CHANGE;
	
	@Override
	public void close() {
	}
	
	@Override
	public boolean isChenge() {
		return false;
	}
	
	@Override
	public T get() {
		return null;
	}
	
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
}
