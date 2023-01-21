package com.greentree.engine.moon.render.ui;

import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent(Transform.class)
public record Text(String value) implements ConstComponent {
	
	@Override
	public String toString() {
		return "Text [" + value + "]";
	}
	
}
