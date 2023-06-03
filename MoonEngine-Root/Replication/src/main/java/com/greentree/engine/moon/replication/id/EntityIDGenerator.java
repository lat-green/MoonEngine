package com.greentree.engine.moon.replication.id;

import com.greentree.engine.moon.base.property.world.ReadSceneProperty;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import com.greentree.engine.moon.replication.ReplicationComponent;

public class EntityIDGenerator implements WorldInitSystem, UpdateSystem, DestroySystem {

    private static final FilterBuilder CREATE_ID = new FilterBuilder().require(ReplicationComponent.class).ignore(EntityID.class);

    private EntityIDs ids;
    private Filter<?> createID;

    @Override
    public void destroy() {
        createID = null;
    }

    @Override
    public void update() {
        for (var e : createID)
            ids.addID(e);
    }

    @ReadSceneProperty({EntityIDs.class})
    @Override
    public void init(World world, SceneProperties sceneProperties) {
        createID = CREATE_ID.build(world);
        ids = sceneProperties.get(EntityIDs.class);
    }

}
