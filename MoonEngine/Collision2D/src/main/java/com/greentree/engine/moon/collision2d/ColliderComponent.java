package com.greentree.engine.moon.collision2d;

import com.greentree.commons.geometry.geom2d.IMovableShape2D;
import com.greentree.engine.moon.ecs.component.ConstComponent;

import java.io.Serial;
import java.util.Objects;

public final class ColliderComponent implements ConstComponent {

    @Serial
    private static final long serialVersionUID = 0L;
    private final IMovableShape2D shape;

    public ColliderComponent(IMovableShape2D shape) {
        this.shape = shape;
    }

    public IMovableShape2D shape() {
        return shape;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shape);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ColliderComponent) obj;
        return Objects.equals(this.shape, that.shape);
    }

    @Override
    public String toString() {
        return "ColliderComponent[" +
                "shape=" + shape + ']';
    }

}
