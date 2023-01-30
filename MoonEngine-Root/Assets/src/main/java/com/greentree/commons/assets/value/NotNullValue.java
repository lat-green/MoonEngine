package com.greentree.commons.assets.value;

import com.greentree.commons.assets.value.provider.NotNullProvider;
import com.greentree.commons.assets.value.provider.ValueProvider;

public final class NotNullValue<T> implements Value<T> {
	
	private static final long serialVersionUID = 1L;
	private final Value<T> value;
	
	private NotNullValue(Value<T> value) {
		this.value = value;
	}
	
	@Override
	public int characteristics() {
		return NotNullProvider.CHARACTERISTICS;
	}
	
	@Override
	public ValueProvider<T> openProvider() {
		return NotNullProvider.newProvider(value);
	}
	
	public static <T> Value<T> newValue(Value<T> value) {
		if(value.hasCharacteristics(NOT_NULL))
			return value;
		return new NotNullValue<>(value);
	}
	
	
	
}
