package com.greentree.engine.moon.base.name;

import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;

public class NameSystem implements WorldInitSystem {

    private static final FilterBuilder BUILDER = new FilterBuilder().require(Name.class);

    @CreateProperty({Names.class})
    @Override
    public void init(World world, SceneProperties properties) {
        var names = new Names(BUILDER.build(world));
        properties.add(names);
    }

}
