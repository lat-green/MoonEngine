package com.greentree.engine.moon.render.pipeline.material;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public final class MaterialPropertiesBase implements MaterialProperties {
	
	protected final Map<String, Property> properties = new HashMap<>();
	
	@Override
	public Property get(String name) {
		return properties.get(name);
	}
	
	@Override
	public Iterator<String> iterator() {
		return properties.keySet().iterator();
	}
	
	@Override
	public void put(String name, Property property) {
		properties.put(name, property);
	}
	
	@Override
	public String toString() {
		return "MaterialPropertiesBase " + properties;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(properties);
	}
	
	@Override
	public void set(Shader shader, MaterialProperties last, PropertyBindContext context) {
		for(var entry : properties.entrySet()) {
			final var name = entry.getKey();
			final var property = entry.getValue();
			final var last_property = last.get(name);
			if(Objects.equals(last_property, property))
				continue;
			final var location = shader.getProperty(name);
			property.bind(location, context);
		}
	}
	
	@Override
	public void set(Shader shader, PropertyBindContext context) {
		for(var entry : properties.entrySet()) {
			final var name = entry.getKey();
			final var property = entry.getValue();
			final var location = shader.getProperty(name);
			property.bind(location, context);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof MaterialPropertiesBase)) {
			return false;
		}
		MaterialPropertiesBase other = (MaterialPropertiesBase) obj;
		return Objects.equals(properties, other.properties);
	}
	
}
