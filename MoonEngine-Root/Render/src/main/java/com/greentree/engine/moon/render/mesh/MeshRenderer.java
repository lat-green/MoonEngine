package com.greentree.engine.moon.render.mesh;

import java.util.Objects;

import com.greentree.engine.moon.assets.value.provider.ValueProvider;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.pipeline.material.Material;

public record MeshRenderer(ValueProvider<Material> material) implements ConstComponent {
	
	public MeshRenderer {
		Objects.requireNonNull(material);
	}
	
	@Override
	public void close() {
		material.close();
	}
	
}
