package com.greentree.engine.moon.demo1;

import com.greentree.engine.moon.assets.Asset;
import com.greentree.engine.moon.assets.serializator.manager.AssetManagerKt;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.base.scene.SceneManager;
import com.greentree.engine.moon.base.scene.SceneManagerProperty;
import com.greentree.engine.moon.ecs.scene.Scene;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;

public class InitSceneModule implements LaunchModule {

    private SceneManager scenes;
    private Asset<Scene> scene;

    @WriteProperty({SceneManagerProperty.class})
    @ReadProperty({AssetManagerProperty.class})
    @Override
    public void launch(EngineProperties context) {
        final var manager = context.get(AssetManagerProperty.class).manager;
        scenes = context.get(SceneManagerProperty.class).manager();
        scene = AssetManagerKt.load(manager, Scene.class, "scene/world1.scene");
        scenes.set(scene.getValue());
    }

}
