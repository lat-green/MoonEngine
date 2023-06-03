package com.greentree.engine.moon.render.window;

import com.greentree.engine.moon.base.property.world.ReadSceneProperty;
import com.greentree.engine.moon.base.property.world.WriteSceneProperty;
import com.greentree.engine.moon.base.scene.EnginePropertiesSceneProperty;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.modules.ExitManager;
import com.greentree.engine.moon.modules.property.ExitManagerProperty;

public class ExitOnWindowShouldClose implements InitSystem, DestroySystem, UpdateSystem {

    private Window window;
    private ExitManager manager;

    @Override
    public void destroy() {
        window = null;
        manager = null;
    }

    @WriteSceneProperty({EnginePropertiesSceneProperty.class})
    @ReadSceneProperty({WindowProperty.class})
    @Override
    public void init(SceneProperties properties) {
        window = properties.get(WindowProperty.class).window();
        manager = properties.get(EnginePropertiesSceneProperty.class).properties().get(ExitManagerProperty.class).manager();
    }

    @WriteSceneProperty({EnginePropertiesSceneProperty.class})
    @Override
    public void update() {
        if (window.isShouldClose())
            manager.exit();
    }

}
