package com.greentree.engine.moon.collision2d;

import com.greentree.commons.geometry.geom2d.collision.CollisionEvent2D;
import com.greentree.engine.moon.ecs.component.Component;

import java.util.Objects;

public class ColliderEventComponent implements Component {

    private CollisionEvent2D<Collidable2DEntity, Collidable2DEntity> event;

    public ColliderEventComponent() {
    }

    public ColliderEventComponent(CollisionEvent2D<Collidable2DEntity, Collidable2DEntity> event) {
        set(event);
    }

    public void set(CollisionEvent2D<Collidable2DEntity, Collidable2DEntity> event) {
        Objects.requireNonNull(event);
        this.event = event;
    }

    @Override
    public String toString() {
        return "ColliderEventComponent[" + event + "]";
    }

    public CollisionEvent2D<Collidable2DEntity, Collidable2DEntity> event() {
        return event;
    }

    @Override
    public Component copy() {
        if (event == null)
            return new ColliderEventComponent();
        return new ColliderEventComponent(event);
    }

}
