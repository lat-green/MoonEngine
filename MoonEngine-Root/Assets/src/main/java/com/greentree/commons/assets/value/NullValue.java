package com.greentree.commons.assets.value;

import java.io.ObjectStreamException;

import com.greentree.commons.assets.value.provider.NullProvider;
import com.greentree.commons.assets.value.provider.ValueProvider;

public final class NullValue<T> implements Value<T> {
	
	private static final long serialVersionUID = 1L;
	
	private static final NullValue<?> INSTANCE = new NullValue<>();
	
	private Object readResolve() throws ObjectStreamException {
		return INSTANCE;
	}
	
	private NullValue() {
	}
	
	@SuppressWarnings("unchecked")
	public static <T> NullValue<T> instance() {
		return (NullValue<T>) INSTANCE;
	}
	
	@Override
	public String toString() {
		return "NULL";
	}
	
	@Override
	public ValueProvider<T> openProvider() {
		return NullProvider.instance();
	}
	
	@Override
	public int characteristics() {
		return NullProvider.CHARACTERISTICS;
	}
	
}
