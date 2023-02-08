package com.greentree.engine.moon.render.pipeline.material;

public final class MaterialPropertiesWithParent extends ProxyMaterialProperties {
	
	private final MaterialProperties parent;
	
	public MaterialPropertiesWithParent(MaterialProperties parent) {
		this(parent, new MaterialPropertiesBase());
	}
	
	
	public MaterialPropertiesWithParent(MaterialProperties parent, MaterialProperties base) {
		super(base);
		this.parent = parent;
	}
	
	
	@Override
	public void set(Shader shader) {
		parent.set(shader);
		base.set(shader);
	}
	
	@Override
	public MaterialProperties diff(MaterialProperties other) {
		if(other instanceof MaterialPropertiesWithParent m) {
			if(parent.equals(m.parent)) {
				final var diff = base.diff(m.base);
				return diff;
			}
			throw new UnsupportedOperationException("no realization");
		}
		return other.diff(this);
	}
	
}
