package com.greentree.engine.moon.replication.event;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.replication.id.EntityIDs;

public record RemoveEntityEvent(int entity) implements EntityEvent {

    @Override
    public void accept(World world, SceneProperties properties) {
        final var e = properties.get(EntityIDs.class).get(entity);
        e.delete();
    }

}
