package com.greentree.engine.moon.demo1;

import com.greentree.engine.moon.assets.provider.AssetProvider;
import com.greentree.engine.moon.assets.provider.AssetProviderKt;
import com.greentree.engine.moon.assets.provider.request.EmptyAssetRequest;
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
    private AssetProvider<Scene> scene;
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
        lastUpdate = AssetProviderKt.getLastModified(scene);
        scenes.set(scene.value(EmptyAssetRequest.INSTANCE));
    }

    @Override
    public void update() {
        var newUpdate = AssetProviderKt.getLastModified(scene);
        if (newUpdate > lastUpdate) {
            lastUpdate = newUpdate;
            scenes.set(scene.value(EmptyAssetRequest.INSTANCE));
        }
    }

}
