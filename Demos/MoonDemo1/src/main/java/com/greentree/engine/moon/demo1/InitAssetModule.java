package com.greentree.engine.moon.demo1;

import com.greentree.commons.data.resource.location.RootFileResourceLocation;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;

public class InitAssetModule implements LaunchModule {

    @WriteProperty({AssetManagerProperty.class})
    @Override
    public void launch(EngineProperties context) {
        final var manager = context.get(AssetManagerProperty.class).manager;
        manager.addResourceLocation(new RootFileResourceLocation("Assets"));
//        manager.addResourceLocation(new ClassLoaderResourceLocation(Main.class));
    }

}
