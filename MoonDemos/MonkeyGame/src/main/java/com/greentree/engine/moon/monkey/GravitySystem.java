package com.greentree.engine.moon.monkey;

import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.base.component.WriteComponent;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.time.Time;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;

public class GravitySystem implements WorldInitSystem, UpdateSystem {

    private static final FilterBuilder BUILDER = new FilterBuilder()
            .require(Gravity.class);

    private Filter<?> filter;

    private float value;
    private Time time;

    @WriteComponent({Velocity.class})
    @ReadComponent({Gravity.class})
    @Override
    public void update() {
        for (var e : filter) {
            var t = e.get(Velocity.class).value;
            t.y(t.y() - value * time.delta());
        }
    }

    @ReadProperty({Time.class})
    @Override
    public void init(World world, SceneProperties sceneProperties) {
        filter = BUILDER.build(world);
        time = sceneProperties.get(Time.class);
    }

}
