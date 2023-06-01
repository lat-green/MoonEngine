package com.greentree.engine.moon.ecs.pool;

import com.greentree.engine.moon.ecs.Entity;

import java.io.Serializable;
import java.util.Comparator;

public interface EntityPoolStrategy extends Serializable {

    Comparator<Entity> comporator();

    void toInstance(Entity entity);

}
