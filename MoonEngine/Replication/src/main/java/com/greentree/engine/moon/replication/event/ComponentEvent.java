package com.greentree.engine.moon.replication.event;

import com.greentree.engine.moon.ecs.ComponentLock;
import com.greentree.engine.moon.ecs.Entity;

import java.io.Serializable;

public interface ComponentEvent extends Serializable {

    void accept(Entity entity, ComponentLock lock);

}
