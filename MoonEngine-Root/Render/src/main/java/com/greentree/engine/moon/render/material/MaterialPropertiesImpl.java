package com.greentree.engine.moon.render.material;

import java.util.HashMap;
import java.util.Map;

public final class MaterialPropertiesImpl implements MaterialProperties {
	
	private static final long serialVersionUID = 1L;
	
	private final Map<String, Property> properties;
	
	public MaterialPropertiesImpl() {
		properties = new HashMap<>();
	}
	
	public MaterialPropertiesImpl(Map<? extends String, ? extends Property> properties) {
		this.properties = new HashMap<>(properties);
	}
	
	@Override
	public Property get(String name) {
		return properties.get(name);
	}
	
	@Override
	public Iterable<? extends String> getNames() {
		return properties.keySet();
	}
	
	@Override
	public boolean has(String name) {
		return properties.containsKey(name);
	}
	
	@Override
	public void put(String name, Property property) {
		properties.put(name, property);
	}
	
	@Override
	public Property remove(String name) {
		return properties.remove(name);
	}
	
	@Override
	public String toString() {
		return "MaterialProperties " + properties;
	}
	
}
