package com.greentree.engine.moon.replication.event;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.replication.id.EntityIDs;

public record ComponentEntityEvent(int entity, Iterable<? extends ComponentEvent> events) implements EntityEvent {

    @Override
    public void accept(World world, SceneProperties properties) {
        final var e = properties.get(EntityIDs.class).get(entity);
        try (final var lock = e.lock()) {
            for (var event : events)
                event.accept(e, lock);
        } catch (Exception e1) {
            throw new RuntimeException("" + this, e1);
        }
    }

}
