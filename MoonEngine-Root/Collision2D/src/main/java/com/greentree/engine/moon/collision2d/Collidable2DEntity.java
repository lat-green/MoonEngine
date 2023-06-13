package com.greentree.engine.moon.collision2d;

import com.greentree.commons.geometry.geom2d.IShape2D;
import com.greentree.commons.geometry.geom2d.collision.Collidable2D;
import com.greentree.engine.moon.ecs.Entity;

public record Collidable2DEntity(Entity entity) implements Collidable2D {

    @Override
    public IShape2D getShape() {
        return entity.get(ColliderComponent.class).shape();
    }

}
