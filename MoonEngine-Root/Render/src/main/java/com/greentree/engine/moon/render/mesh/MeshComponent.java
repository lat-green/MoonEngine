package com.greentree.engine.moon.render.mesh;

import java.util.Objects;

import com.greentree.common.ecs.annotation.RequiredComponent;
import com.greentree.common.ecs.component.ConstComponent;
import com.greentree.common.renderer.mesh.GraphicsMesh;
import com.greentree.commons.assets.value.Value;
import com.greentree.engine.moon.base.transform.Transform;

@RequiredComponent({Transform.class})
public record MeshComponent(Value<? extends GraphicsMesh> mesh) implements ConstComponent {
	
	
	public MeshComponent {
		Objects.requireNonNull(mesh);
	}
	
	@Override
	public String toString() {
		var builder = new StringBuilder();
		builder.append("MeshComponent [");
		builder.append(mesh);
		builder.append("]");
		return builder.toString();
	}
	
}
