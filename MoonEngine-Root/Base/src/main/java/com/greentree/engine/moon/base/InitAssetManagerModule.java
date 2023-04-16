package com.greentree.engine.moon.base;

import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.modules.CreateProperty;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;


public class InitAssetManagerModule implements LaunchModule {
	
	@CreateProperty({AssetManagerProperty.class})
	@Override
	public void launch(EngineProperties context) {
		final var manager = new AssetManager();
		context.add(new AssetManagerProperty(manager));
	}
	
}
