package com.greentree.engine.moon.render.pipeline.material;

import com.greentree.engine.moon.render.texture.Texture;

public record TextureProperty(Texture texture) implements Property {
	
	@Override
	public void bind(PropertyLocation property, PropertyBindContext context) {
		final var slot = context.nextEmptyTextureSlot();
		property.setInt(slot);
		texture.bint(slot);
	}
	
}
