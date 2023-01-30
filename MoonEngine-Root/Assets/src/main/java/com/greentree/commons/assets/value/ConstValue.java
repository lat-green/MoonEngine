package com.greentree.commons.assets.value;

import com.greentree.commons.assets.value.provider.ConstProvider;
import com.greentree.commons.assets.value.provider.ValueProvider;

public final class ConstValue<T> implements Value<T> {
	
	private static final long serialVersionUID = 1L;
	
	private final T value;
	
	private ConstValue(T value) {
		this.value = value;
	}
	
	public static <T> Value<T> newValue(T value) {
		if(value == null)
			return NullValue.instance();
		return new ConstValue<>(value);
	}
	
	@Override
	public String toString() {
		return "Const [" + value + "]";
	}
	
	@Override
	public ValueProvider<T> openProvider() {
		return ConstProvider.newValue(value);
	}
	
	@Override
	public int characteristics() {
		return ConstProvider.CHARACTERISTICS;
	}
	
}
