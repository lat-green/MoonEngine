package com.greentree.engine.moon.render.pipeline.material;

import java.util.Iterator;
import java.util.Objects;

import com.greentree.commons.util.iterator.IteratorUtil;

public final class MaterialPropertiesWithParent implements MaterialProperties {
	
	private final MaterialProperties parent, base;
	
	public MaterialPropertiesWithParent(MaterialProperties parent) {
		this(parent, new MaterialPropertiesBase());
	}
	
	
	public MaterialPropertiesWithParent(MaterialProperties parent, MaterialProperties base) {
		this.parent = parent;
		this.base = base;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof MaterialPropertiesWithParent other))
			return false;
		return Objects.equals(parent, other.parent) && Objects.equals(base, other.base);
	}
	
	@Override
	public Property get(String name) {
		Property property;
		property = base.get(name);
		if(property != null)
			return property;
		property = parent.get(name);
		return property;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(parent, base);
	}
	
	@Override
	public Iterator<String> iterator() {
		return IteratorUtil.union(parent, base).iterator();
	}
	
	
	@Override
	public void put(String name, Property property) {
		base.put(name, property);
	}
	
}
