package com.greentree.engine.moon.modules;

import java.util.Optional;

public interface EngineProperties extends Iterable<EngineProperty> {
	
	void add(EngineProperty property);
	
	default <T extends EngineProperty> T get(Class<T> cls) {
		final Optional<T> property;
		try {
			property = getProperty(cls);
		}catch(Exception e) {
			throw new IllegalArgumentException("not found property of class: " + cls, e);
		}
		return property.orElseThrow(() -> new IllegalArgumentException("not found property of class: " + cls));
	}
	
	default <T> T getData(Class<T> cls) {
		final Optional<T> property;
		try {
			property = getPropertyData(cls);
		}catch(Exception e) {
			throw new IllegalArgumentException("not found property data of class: " + cls, e);
		}
		return property.orElseThrow(() -> new IllegalArgumentException("not found property data of class: " + cls));
	}
	
	default boolean has(Class<? extends EngineProperty> cls) {
		return getProperty(cls).isPresent();
	}
	
	<T extends EngineProperty> Optional<T> getProperty(Class<T> cls);
	
	<T> Optional<T> getPropertyData(Class<T> cls);
	
}
