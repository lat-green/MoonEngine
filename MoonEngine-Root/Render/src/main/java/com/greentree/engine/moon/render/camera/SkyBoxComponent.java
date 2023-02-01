package com.greentree.engine.moon.render.camera;

import com.greentree.engine.moon.assets.value.provider.ValueProvider;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.material.Material;

public record SkyBoxComponent(ValueProvider<Material> material) implements ConstComponent {
	
	@Override
	public void close() {
		material.close();
	}
}
