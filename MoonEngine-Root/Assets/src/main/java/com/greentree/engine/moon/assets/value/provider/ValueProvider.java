package com.greentree.engine.moon.assets.value.provider;

import java.util.function.Consumer;

import com.greentree.engine.moon.assets.value.getter.ValueGetter;

public interface ValueProvider<T> extends ValueGetter<T> {
	
	
	T get();
	
	default boolean isNull() {
		return !hasCharacteristics(NOT_NULL) && get() == null;
	}
	
	boolean isChenge();
	
	default boolean tryGet(Consumer<? super T> action) {
		if(!isChenge())
			return false;
		action.accept(get());
		return true;
	}
	
	ValueProvider<T> copy();
	
}
