package com.greentree.engine.moon.collision2d;

import com.greentree.commons.geometry.geom2d.IShape2D;
import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.engine.moon.ecs.WorldEntity;

public record Collidable2DEntity(WorldEntity entity, IShape2D shape) implements Collidable2D {

    @Override
    public IShape2D getShape() {
        return shape;
    }

}
