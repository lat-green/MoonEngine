package com.greentree.engine.moon.render.ui;

import com.greentree.common.ecs.annotation.RequiredComponent;
import com.greentree.common.ecs.component.ConstComponent;
import com.greentree.engine.moon.base.transform.Transform;

@RequiredComponent(Transform.class)
public record Text(String value) implements ConstComponent {
	
	@Override
	public String toString() {
		return "Text [" + value + "]";
	}
	
}
