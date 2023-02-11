package com.greentree.engine.moon.assets.value.getter;

import com.greentree.engine.moon.assets.value.ValueCharacteristics;

public interface ValueGetter<T> extends ValueCharacteristics<T>, AutoCloseable {
	
	default T getNotChenge() {
		return get();
	}
	
	T get();
	
	
	@Override
	void close();
	
	ValueGetter<T> copy();
	
}
