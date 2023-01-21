package com.greentree.engine.moon.render;

import java.util.Objects;

import com.greentree.common.renderer.material.Material;
import com.greentree.commons.assets.value.Value;
import com.greentree.engine.moon.ecs.component.ConstComponent;

public record MeshRenderer(Value<Material> material) implements ConstComponent {
	
	public MeshRenderer {
		Objects.requireNonNull(material);
	}
	
}
