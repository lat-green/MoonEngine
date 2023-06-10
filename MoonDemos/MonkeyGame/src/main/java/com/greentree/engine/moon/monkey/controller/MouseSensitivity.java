package com.greentree.engine.moon.monkey.controller;

import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({Transform.class})
public record MouseSensitivity(float sensitivityX, float sensitivityY) implements ConstComponent {

}

