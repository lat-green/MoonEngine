package com.greentree.commons.assets.value;

import java.util.Objects;

import com.greentree.commons.assets.value.provider.ReduceProvider;
import com.greentree.commons.assets.value.provider.ValueProvider;

public final class ReduceValue<T> implements Value<T> {
	
	private static final long serialVersionUID = 1L;
	
	private final MutableValue<Value<T>> result = new MutableValue<>();
	
	public ReduceValue(Value<T> Value) {
		set(Value);
	}
	
	public void set(Value<T> Value) {
		Objects.requireNonNull(Value);
		result.set(Value);
	}
	
	@Override
	public int characteristics() {
		return ReduceProvider.CHARACTERISTICS;
	}
	
	@Override
	public ValueProvider<T> openProvider() {
		return ReduceProvider.newValue(result);
	}
	
}
