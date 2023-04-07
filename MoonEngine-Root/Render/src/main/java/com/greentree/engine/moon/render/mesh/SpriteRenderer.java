package com.greentree.engine.moon.render.mesh;

import java.util.Objects;

import com.greentree.engine.moon.assets.value.provider.ValueProvider;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.pipeline.material.Property;

@RequiredComponent({Transform.class})
public record SpriteRenderer(ValueProvider<Property> texture) implements ConstComponent {
	
	public SpriteRenderer {
		Objects.requireNonNull(texture);
	}
	
	@Override
	public void close() {
		texture.close();
	}
	
}
