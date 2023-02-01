package com.greentree.engine.moon.render.shader;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.greentree.engine.moon.render.material.MaterialProperties;
import com.greentree.engine.moon.render.material.MaterialPropertiesImpl;
import com.greentree.engine.moon.render.material.MaterialProperty;

public final class MaterialPropertiesBuilder {
	
	private final Map<String, PropertyType> types = new HashMap<>();
	private final Map<String, MaterialProperty> values = new HashMap<>();
	
	
	public MaterialPropertiesBuilder(Iterable<? extends MaterialField> fields) {
		for(var f : fields)
			types.put(f.name(), f.type());
	}
	
	public void put(String name, MaterialProperty property) {
		Objects.requireNonNull(property);
		if(values.containsKey(name))
			throw new UnsupportedOperationException(name + " already added");
		if(!types.containsKey(name))
			throw new UnsupportedOperationException(
					"uncorrect name:" + name + " for " + types.keySet());
		values.put(name, property);
	}
	
	public PropertyType type(String name) {
		return types.get(name);
	}
	
	public MaterialProperties build() {
		if(values.size() != types.size())
			throw new UnsupportedOperationException(
					"not all values put " + types.keySet() + " " + values.keySet());
		return new MaterialPropertiesImpl(values);
	}
	
}
