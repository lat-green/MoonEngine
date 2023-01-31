package com.greentree.engine.moon.base;

import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.annotation.CreateProperty;


public class InitAssetManagerModule implements LaunchModule {
	
	@CreateProperty({AssetManagerProperty.class})
	@Override
	public void launch(EngineProperties context) {
		final var manager = new AssetManager();
		context.add(new AssetManagerProperty(manager));
	}
	
}
