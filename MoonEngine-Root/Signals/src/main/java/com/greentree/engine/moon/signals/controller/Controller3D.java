package com.greentree.engine.moon.signals.controller;

import com.greentree.common.ecs.annotation.RequiredComponent;
import com.greentree.common.ecs.component.ConstComponent;
import com.greentree.commons.math.Mathf;
import com.greentree.engine.moon.base.transform.Transform;

@RequiredComponent({Transform.class})
public record Controller3D(float speed) implements ConstComponent {
	
	public static final float BORDER = Mathf.PI / 2 * 0.999f;
	
	public Controller3D {
	}
	
	public Controller3D() {
		this(1);
	}
	
}
