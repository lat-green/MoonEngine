package com.greentree.engine.moon.modules.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.EngineProperty;

public final class EnginePropertiesBase implements EngineProperties {
	
	private final Map<Class<?>, EngineProperty> properties = new HashMap<>();
	
	@Override
	public void add(EngineProperty property) {
		final var cls = property.getClass();
		if(properties.containsKey(cls))
			throw new IllegalArgumentException("property of " + cls.getName() + " already added");
		properties.put(cls, property);
	}
	
	public void clear() {
		properties.clear();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends EngineProperty> T get(Class<T> cls) {
		if(!properties.containsKey(cls))
			throw new IllegalArgumentException("property of " + cls.getName() + " not added");
		return (T) properties.get(cls);
	}
	
	@Override
	public boolean has(Class<? extends EngineProperty> cls) {
		return properties.containsKey(cls);
	}
	
	@Override
	public Iterator<EngineProperty> iterator() {
		return properties.values().iterator();
	}
	
}
