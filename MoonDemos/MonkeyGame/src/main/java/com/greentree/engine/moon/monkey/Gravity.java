package com.greentree.engine.moon.monkey;

import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent(Velocity.class)
public record Gravity() implements ConstComponent {

}
