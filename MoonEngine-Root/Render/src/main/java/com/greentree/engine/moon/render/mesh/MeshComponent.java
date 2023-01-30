package com.greentree.engine.moon.render.mesh;

import java.util.Objects;

import com.greentree.common.renderer.mesh.GraphicsMesh;
import com.greentree.commons.assets.value.provider.ValueProvider;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({Transform.class})
public record MeshComponent(ValueProvider<? extends GraphicsMesh> mesh) implements ConstComponent {
	
	
	public MeshComponent {
		Objects.requireNonNull(mesh);
		System.out.println(mesh);
	}
	
	@Override
	public void close() {
		mesh.close();
	}
	
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
