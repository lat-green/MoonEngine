package com.greentree.engine.moon.render;

import java.util.Objects;

import com.greentree.common.renderer.material.Material;
import com.greentree.commons.assets.value.provider.ValueProvider;
import com.greentree.engine.moon.ecs.component.ConstComponent;

public record MeshRenderer(ValueProvider<Material> material) implements ConstComponent {
	
	public MeshRenderer {
		Objects.requireNonNull(material);
	}
	
	@Override
	public void close() {
		material.close();
	}
	
}
