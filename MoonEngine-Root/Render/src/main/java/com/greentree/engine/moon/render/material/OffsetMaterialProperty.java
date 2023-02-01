package com.greentree.engine.moon.render.material;

import com.greentree.engine.moon.render.shader.OffsetUniformLocation;
import com.greentree.engine.moon.render.shader.UniformLocation;

public record OffsetMaterialProperty(MaterialProperty base, int offset)
		implements MaterialProperty {
	
	@Override
	public void set(UniformLocation location) {
		base.set(new OffsetUniformLocation(location, offset));
	}
	
	static MaterialProperty offset(MaterialProperty property, int offset) {
		if(offset == 0)
			return property;
		if(property instanceof OffsetMaterialProperty p) {
			if(p.offset + offset == 0)
				return property;
		}
		return new OffsetMaterialProperty(property, offset);
	}
	
	
	
}
