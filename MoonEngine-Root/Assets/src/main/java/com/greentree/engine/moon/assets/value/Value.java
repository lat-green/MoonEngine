package com.greentree.engine.moon.assets.value;

import java.io.Serializable;

import com.greentree.engine.moon.assets.value.getter.ValueGetter;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

public interface Value<T> extends ValueCharacteristics<T>, Serializable {
	
	ValueProvider<T> openProvider();
	
	default ValueGetter<T> openGetter() {
		return openProvider();
	}
	
	default T get() {
		return openGetter().get();
	}
	
	default boolean isNull() {
		return !hasCharacteristics(NOT_NULL) && get() == null;
	}
}
