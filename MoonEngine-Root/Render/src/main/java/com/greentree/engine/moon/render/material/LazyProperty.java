package com.greentree.engine.moon.render.material;

import java.util.function.Supplier;

public record LazyProperty(Supplier<Property> value) implements Property {
	
	@Override
	public void bind(PropertyLocation location) {
		value.get().bind(location);
	}
	
}
