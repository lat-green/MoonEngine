package com.greentree.commons.assets.value;

import java.io.Serializable;

import com.greentree.commons.assets.value.provider.ValueProvider;

public interface Value<T> extends ValueCharacteristics<T>, Serializable {
	
	ValueProvider<T> openProvider();
	
	@Deprecated
	default T get() {
		try(final var provider = openProvider()) {
			return provider.get();
		}
	}
	
	@Deprecated
	default boolean isNull() {
		return !hasCharacteristics(NOT_NULL) && get() == null;
	}
}
