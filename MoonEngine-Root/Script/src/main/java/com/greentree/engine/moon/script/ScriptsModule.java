package com.greentree.engine.moon.script;

import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.WriteProperty;
import com.greentree.engine.moon.script.assets.ScriptAssetSerializator;


public class ScriptsModule implements LaunchModule {
	
	@WriteProperty({AssetManagerProperty.class})
	@Override
	public void launch(EngineProperties context) {
		final var manager = context.get(AssetManagerProperty.class).manager();
		
		manager.addSerializator(new ScriptAssetSerializator());
		
	}
	
}
