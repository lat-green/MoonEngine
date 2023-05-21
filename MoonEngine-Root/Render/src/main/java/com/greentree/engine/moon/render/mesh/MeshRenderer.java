package com.greentree.engine.moon.render.mesh;

import java.util.Objects;

import com.greentree.engine.moon.assets.value.provider.ValueProvider;
import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.material.Material;

@RequiredComponent({MeshComponent.class})
public record MeshRenderer(ValueProvider<Material> material) implements ConstComponent {
	
	public MeshRenderer {
		Objects.requireNonNull(material);
	}
	
}
