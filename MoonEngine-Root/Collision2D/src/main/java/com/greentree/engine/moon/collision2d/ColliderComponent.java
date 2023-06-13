package com.greentree.engine.moon.collision2d;

import com.greentree.commons.geometry.geom2d.IMovableShape2D;
import com.greentree.engine.moon.ecs.component.ConstComponent;

public record ColliderComponent(IMovableShape2D shape) implements ConstComponent {

}
