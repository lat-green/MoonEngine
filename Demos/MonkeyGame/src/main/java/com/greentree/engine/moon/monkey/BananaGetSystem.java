package com.greentree.engine.moon.monkey;

import com.greentree.engine.moon.collision2d.ColliderEventComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;

public class BananaGetSystem implements WorldInitSystem, UpdateSystem {

    private static final FilterBuilder BUILDER = new FilterBuilder().require(ColliderEventComponent.class);

    private Filter<?> filter;

    @Override
    public void update() {
        for (var e : filter.safe()) {
            var event = e.get(ColliderEventComponent.class).event();
            var a = event.a.entity();
            var b = event.b.entity();
            if (a.contains(Banana.class) && b.contains(Player.class)) {
                a.delete();
                b.get(Player.class).score++;
            }
            if (b.contains(Banana.class) && a.contains(Player.class)) {
                b.delete();
                a.get(Player.class).score++;
            }
        }
    }

    @Override
    public void init(World world, SceneProperties sceneProperties) {
        filter = BUILDER.build(world);
    }

}
