package com.greentree.engine.moon.render;

import java.util.Objects;

import com.greentree.common.ecs.component.ConstComponent;
import com.greentree.common.renderer.material.Material;
import com.greentree.commons.assets.value.Value;

public record MeshRenderer(Value<Material> material) implements ConstComponent {
	
	public MeshRenderer {
		Objects.requireNonNull(material);
	}
	
}
