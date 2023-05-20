package com.greentree.engine.moon.modules;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import com.greentree.commons.util.classes.ClassUtil;

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
	
	
	@Override
	public <T> Optional<T> getPropertyData(Class<T> cls) {
		for(var property : this) {
			var result = getPropertyDataInField(property, cls);
			if(result != null) {
				return Optional.of(result);
			}
		}
		return Optional.empty();
	}
	
	private static <T> T getPropertyDataInField(Object obj, Class<T> fieldType) {
		var cls = obj.getClass();
		for(var f : ClassUtil.getAllNotStaticFields(cls)) {
			if(ClassUtil.isExtends(fieldType, f.getType()))
				try {
					return (T) ClassUtil.getField(obj, f);
				}catch(IllegalArgumentException | IllegalAccessException e) {
				}
		}
		return null;
	}
	
}
