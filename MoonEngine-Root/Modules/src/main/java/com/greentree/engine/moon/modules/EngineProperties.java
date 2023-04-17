package com.greentree.engine.moon.modules;

import java.util.Optional;

public interface EngineProperties extends Iterable<EngineProperty> {
	
	void add(EngineProperty property);
	
	default <T extends EngineProperty> T get(Class<T> cls) {
		return getProperty(cls).get();
	}
	
	default boolean has(Class<? extends EngineProperty> cls) {
		return getProperty(cls).isPresent();
	}
	
	<T extends EngineProperty> Optional<T> getProperty(Class<T> cls);
	
}
