package com.greentree.engine.moon.monkey;

import com.greentree.commons.math.Mathf;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;

public class MinYSystem implements WorldInitSystem, UpdateSystem {

    private static final FilterBuilder BUILDER = new FilterBuilder()
            .require(MinY.class);

    private Filter<?> filter;

    @Override
    public void update() {
        for (var e : filter) {
            var t = e.get(Transform.class).position;
            var minY = e.get(MinY.class).value();
            t.y(Mathf.max(t.y(), minY));
        }
    }

    @Override
    public void init(World world, SceneProperties sceneProperties) {
        filter = BUILDER.build(world);
    }

}
