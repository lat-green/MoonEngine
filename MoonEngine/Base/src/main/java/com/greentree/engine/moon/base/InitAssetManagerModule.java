package com.greentree.engine.moon.base;

import com.greentree.engine.moon.assets.manager.SimpleAssetManager;
import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;

public class InitAssetManagerModule implements LaunchModule {

    @CreateProperty({AssetManagerProperty.class})
    @Override
    public void launch(EngineProperties context) {
        final var manager = new SimpleAssetManager();
        context.add(new AssetManagerProperty(manager));
    }

}
