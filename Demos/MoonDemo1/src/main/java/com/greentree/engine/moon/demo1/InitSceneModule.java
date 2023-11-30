package com.greentree.engine.moon.demo1;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetCharacteristicsKt;
import com.greentree.engine.moon.assets.serializator.manager.AsyncAssetManagerKt;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.base.scene.SceneManager;
import com.greentree.engine.moon.base.scene.SceneManagerProperty;
import com.greentree.engine.moon.ecs.scene.Scene;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.UpdateModule;
import com.greentree.engine.moon.modules.property.EngineProperties;

public class InitSceneModule implements LaunchModule, UpdateModule {

    private SceneManager scenes;
    private Asset<Scene> scene;
    private long lastUpdate;

    @WriteProperty({SceneManagerProperty.class})
    @ReadProperty({AssetManagerProperty.class})
    @Override
    public void launch(EngineProperties context) {
        final var manager = context.get(AssetManagerProperty.class).manager;
        scenes = context.get(SceneManagerProperty.class).manager();
        scene = AsyncAssetManagerKt.loadAsync(manager, Scene.class, "scene/world1.xml");
//        scene = AsyncAssetManagerKt.loadAsync(manager, Scene.class, "scene/big.xml");
//        scene = AssetLoaderKt.load(manager, Scene.class, "scene/world1.xml");
        lastUpdate = scene.getLastModified();
        scenes.set(scene.getValue());
    }

    @Override
    public void update() {
        if (AssetCharacteristicsKt.isChange(scene, lastUpdate)) {
            lastUpdate = scene.getLastModified();
            scenes.set(scene.getValue());
        }
    }

}
