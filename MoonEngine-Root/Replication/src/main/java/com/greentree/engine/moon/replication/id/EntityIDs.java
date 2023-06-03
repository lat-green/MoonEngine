package com.greentree.engine.moon.replication.id;

import com.greentree.commons.util.collection.MapID;
import com.greentree.commons.util.collection.MapIDImpl;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.scene.SceneProperty;

import java.util.HashMap;
import java.util.Map;

public final class EntityIDs implements SceneProperty {

    private final Map<Integer, WorldEntity> ids = new HashMap<>();
    private final MapID f = new MapIDImpl();

    public EntityIDs(Filter<?> filter) {
    }

    public boolean contains(int id) {
        return ids.containsKey(id);
    }

    public WorldEntity get(int id) {
        if (!ids.containsKey(id))
            throw new IllegalArgumentException("not entity " + id);
        return ids.get(id);
    }

    public int getFree() {
        return 0;
    }

    void addID(Entity e) {
        final var id = f.get();
        e.add(new EntityID(id));
    }

}
