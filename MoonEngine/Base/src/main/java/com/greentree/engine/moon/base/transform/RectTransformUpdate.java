package com.greentree.engine.moon.base.transform;

import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.base.component.WriteComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.system.SimpleWorldInitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class RectTransformUpdate implements SimpleWorldInitSystem, UpdateSystem {

    private static final FilterBuilder builder = new FilterBuilder().require(RectTransform.class);
    private Filter<? extends WorldEntity> filter;

    @WriteComponent({Transform.class})
    @ReadComponent(RectTransform.class)
    @Override
    public void update() {
        for (var e : filter) {
            final var rt = e.get(RectTransform.class);
            final var tr = e.get(Transform.class);
            final var c = rt.center();
            final var x = rt.xSize();
            final var y = rt.ySize();
            tr.position.set(c.x(), c.y(), 0);
            tr.rotation.identity();
            tr.scale.set(x, y, 1);
        }
    }

    @Override
    public void init(World world) {
        filter = builder.build(world);
    }

}
