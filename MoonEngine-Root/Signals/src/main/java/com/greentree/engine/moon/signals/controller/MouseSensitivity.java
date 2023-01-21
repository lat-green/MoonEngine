package com.greentree.engine.moon.signals.controller;

import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({Transform.class})
public record MouseSensitivity(float sensitivityX, float sensitivityY) implements ConstComponent {
	
}

