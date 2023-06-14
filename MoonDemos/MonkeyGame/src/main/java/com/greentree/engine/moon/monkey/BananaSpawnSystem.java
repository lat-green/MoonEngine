package com.greentree.engine.moon.monkey;

import com.greentree.commons.math.Mathf;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.base.component.WriteComponent;
import com.greentree.engine.moon.base.name.Names;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;

public class BananaSpawnSystem implements WorldInitSystem, UpdateSystem {

    private final int bananaCount = 10;
    private float maxX;

    private Filter<?> filter;
    private WorldEntity bananaPrototype;
    private World world;

    @WriteComponent(Transform.class)
    @Override
    public void update() {
        if (IteratorUtil.size(filter) >= bananaCount)
            return;
        var e = bananaPrototype.copy(world);
        maxX += .3f + Mathf.lerp(-.1f, .1f);
        e.get(Transform.class).position.x(maxX);
        e.get(Transform.class).position.y(Mathf.lerp(-.5f, .5f));
    }

    @ReadProperty(Names.class)
    @Override
    public void init(World world, SceneProperties properties) {
        maxX = .4f;
        this.world = world;
        bananaPrototype = properties.get(Names.class).get("banana-prototype");
        bananaPrototype.deactivate();
        filter = world.newFilterBuilder().require(Banana.class).build();
    }

}
