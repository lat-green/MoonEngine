package com.greentree.engine.moon.render.mesh;

import java.util.Objects;

import com.greentree.engine.moon.assets.value.provider.ValueProvider;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.mesh.StaticMesh;

@RequiredComponent({Transform.class})
public record MeshComponent(ValueProvider<? extends StaticMesh> mesh) implements ConstComponent {
	
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
