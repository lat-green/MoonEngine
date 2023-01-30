package com.greentree.commons.assets.value;

import com.greentree.commons.assets.value.provider.CecheProvider;
import com.greentree.commons.assets.value.provider.ValueProvider;

public final class CecheValue<T> implements Value<T> {
	
	private static final long serialVersionUID = 1L;
	
	private final Value<T> value;
	
	private CecheValue(Value<T> Value) {
		this.value = Value;
	}
	
	public static <T> Value<T> ceche(Value<T> value) {
		if(value.hasCharacteristics(CONST))
			return value;
		if(value.hasCharacteristics(CECHED))
			return value;
		return new CecheValue<>(value);
	}
	
	@Override
	public String toString() {
		return "Ceche [" + value + "]";
	}
	
	@Override
	public int characteristics() {
		return value.characteristics() | CecheProvider.CHARACTERISTICS;
	}
	
	@Override
	public ValueProvider<T> openProvider() {
		return CecheProvider.ceche(value);
	}
	
}
