package com.greentree.engine.moon.modules.property;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

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
	
	@Override
	public Iterator<EngineProperty> iterator() {
		return properties.values().iterator();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends EngineProperty> Optional<T> getProperty(Class<T> cls) {
		return Optional.ofNullable((T) properties.get(cls));
	}
	
}
