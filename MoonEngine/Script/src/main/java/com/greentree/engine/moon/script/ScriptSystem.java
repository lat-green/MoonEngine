package com.greentree.engine.moon.script;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;

public final class ScriptSystem implements WorldInitSystem, UpdateSystem {

    private static final FilterBuilder SCRIPTS = new FilterBuilder().require(Scripts.class);

    private Filter<?> scripts;
    private SceneProperties sceneProperties;

    @Override
    public void update() {
        for (var entity : scripts) {
            final var scripts = entity.get(Scripts.class).scripts();
            for (var value : scripts) {
                final var s = value.getValue();
                for (var c : sceneProperties)
                    s.setConst(c.getClass().getSimpleName(), c);
                for (var c : entity)
                    s.set(c.getClass().getSimpleName(), c);
                try {
                    s.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void init(World world, SceneProperties sceneProperties) {
        this.sceneProperties = sceneProperties;
        scripts = SCRIPTS.build(world);
    }

}
