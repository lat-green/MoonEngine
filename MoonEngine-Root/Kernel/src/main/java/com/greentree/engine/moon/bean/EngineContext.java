package com.greentree.engine.moon.bean;

import java.util.Optional;
import java.util.stream.Stream;

public interface EngineContext {
	
	
	<T> T removeBean(Class<T> cls);
	
	<T> T addBean(T value);
	
	<T> T addBean(Class<T> cls);
	
	default boolean hasBean(Class<?> cls) {
		return getBean(cls).isPresent();
	}
	
	default <T> Optional<T> getBean(Class<T> cls) {
		return getBeans(cls).findAny();
	}
	
	<T> Stream<T> getBeans(Class<T> cls);
	
	boolean isBean(Object bean);
	
}
