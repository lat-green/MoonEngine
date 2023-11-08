package com.greentree.engine.moon.collision2d.assets;

import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;

public class InitAssetsModule implements LaunchModule {

    @WriteProperty(AssetManagerProperty.class)
    @Override
    public void launch(EngineProperties properties) {
        var manager = properties.get(AssetManagerProperty.class).manager;
        manager.addSerializator(new XMLtoShape());
    }

}
