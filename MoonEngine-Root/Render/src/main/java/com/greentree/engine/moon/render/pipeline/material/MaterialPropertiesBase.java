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
