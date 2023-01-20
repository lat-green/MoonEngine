package com.greentree.engine.moon.module;

public interface EngineProperties extends Iterable<EngineProperty> {
	
	void add(EngineProperty property);
	<T extends EngineProperty> T get(Class<T> cls);
	boolean has(Class<? extends EngineProperty> cls);
	
}
