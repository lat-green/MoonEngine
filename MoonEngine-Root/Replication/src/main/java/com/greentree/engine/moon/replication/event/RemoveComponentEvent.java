package com.greentree.engine.moon.replication.event;

import com.greentree.engine.moon.ecs.ComponentLock;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.component.Component;

public record RemoveComponentEvent(Class<? extends Component> cls) implements ComponentEvent {

    @Override
    public void accept(Entity entity, ComponentLock lock) {
        lock.remove(cls);
    }

}
