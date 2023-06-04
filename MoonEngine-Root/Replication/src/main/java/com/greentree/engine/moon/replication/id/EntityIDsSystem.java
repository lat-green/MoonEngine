package com.greentree.engine.moon.replication.id;

import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;

public class EntityIDsSystem implements WorldInitSystem {

    private static final FilterBuilder EntityIDs = new FilterBuilder().require(EntityID.class);

    @CreateProperty({EntityIDs.class})
    @Override
    public void init(World world, SceneProperties sceneProperties) {
        var ids = new EntityIDs(EntityIDs.build(world));
        sceneProperties.add(ids);
    }

}
