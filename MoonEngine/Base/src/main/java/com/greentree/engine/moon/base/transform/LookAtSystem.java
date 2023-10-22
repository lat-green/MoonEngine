package com.greentree.engine.moon.base.transform;

import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.base.component.WriteComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.system.SimpleWorldInitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public final class LookAtSystem implements SimpleWorldInitSystem, UpdateSystem {

    private static final FilterBuilder LOOK_AT = new FilterBuilder().require(LookAt.class);

    private Filter<? extends WorldEntity> look_at;

    @WriteComponent(Transform.class)
    @ReadComponent(LookAt.class)
    @Override
    public void update() {
        for (var e : look_at) {
            final var t = e.get(Transform.class);
            final var l = e.get(LookAt.class).vec();
            var temp = l.minus(t.position);
            t.rotation.identity();
            t.rotation.lookAlong(temp, Transform.UP);
        }
    }

    @Override
    public void init(World world) {
        look_at = LOOK_AT.build(world);
    }

}
