package com.greentree.engine.moon.signals.controller;

import com.greentree.common.ecs.annotation.RequiredComponent;
import com.greentree.common.ecs.component.ConstComponent;
import com.greentree.engine.moon.base.transform.Transform;

@RequiredComponent({Transform.class})
public record MouseSensitivity(float sensitivityX, float sensitivityY) implements ConstComponent {
	
}

