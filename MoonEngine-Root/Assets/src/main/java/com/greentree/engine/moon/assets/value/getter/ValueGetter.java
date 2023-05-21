package com.greentree.engine.moon.assets.value.getter;

import com.greentree.engine.moon.assets.value.ValueCharacteristics;

public interface ValueGetter<T> extends ValueCharacteristics<T> {
	
	default T getNotChenge() {
		return get();
	}
	
	T get();
	
	ValueGetter<T> copy();
	
}
