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
        final var joml_up = Transform.UP.toJoml();
        final var joml_temp = new org.joml.Vector3f();
        for (var e : look_at) {
            final var t = e.get(Transform.class);
            final var l = e.get(LookAt.class).vec();
            var temp = l.minus(t.position);
            joml_temp.set(temp.x(), temp.y(), temp.z());
            t.rotation.identity();
            t.rotation.lookAlong(joml_temp, joml_up);
        }
    }

    @Override
    public void init(World world) {
        look_at = LOOK_AT.build(world);
    }

}
