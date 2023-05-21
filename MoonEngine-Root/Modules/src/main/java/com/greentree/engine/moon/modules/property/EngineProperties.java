package com.greentree.engine.moon.modules.property;

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
	
	default boolean has(Class<? extends EngineProperty> cls) {
		return getProperty(cls).isPresent();
	}
	
	<T extends EngineProperty> Optional<T> getProperty(Class<T> cls);
	
}
