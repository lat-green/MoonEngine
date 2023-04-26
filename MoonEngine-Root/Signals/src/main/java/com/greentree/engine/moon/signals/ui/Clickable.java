package com.greentree.engine.moon.signals.ui;

import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({Transform.class})
public final record Clickable() implements ConstComponent {
	
}
