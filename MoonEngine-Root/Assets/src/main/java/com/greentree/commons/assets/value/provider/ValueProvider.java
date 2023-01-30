package com.greentree.commons.assets.value.provider;

import java.util.function.Consumer;

import com.greentree.commons.assets.value.ValueCharacteristics;

public interface ValueProvider<T> extends ValueCharacteristics<T>, AutoCloseable {
	
	default T getNotChenge() {
		return get();
	}
	
	T get();
	
	@Deprecated
	default boolean isNull() {
		return !hasCharacteristics(NOT_NULL) && get() == null;
	}
	
	@Override
	void close();
	
	boolean isChenge();
	
	default boolean tryGet(Consumer<? super T> action) {
		if(!isChenge())
			return false;
		action.accept(get());
		return true;
	}
	
}
