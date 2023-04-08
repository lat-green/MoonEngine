package com.greentree.engine.moon.render.pipeline.material;

import java.util.function.Supplier;

public record LazyProperty(Supplier<Property> value) implements Property {
	
	@Override
	public void bind(PropertyLocation location, PropertyBindContext context) {
		value.get().bind(location, context);
	}
	
}
