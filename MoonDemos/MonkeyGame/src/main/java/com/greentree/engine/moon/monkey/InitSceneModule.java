package com.greentree.engine.moon.monkey;

import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.base.scene.SceneManagerProperty;
import com.greentree.engine.moon.ecs.scene.Scene;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;

public class InitSceneModule implements LaunchModule {

    @WriteProperty({SceneManagerProperty.class})
    @ReadProperty({AssetManagerProperty.class})
    @Override
    public void launch(EngineProperties context) {
        final var manager = context.get(AssetManagerProperty.class).manager();
        final var scenes = context.get(SceneManagerProperty.class).manager();
        final var scene = manager.loadData(Scene.class, "game.xml");
        scenes.set(scene);
    }

}
