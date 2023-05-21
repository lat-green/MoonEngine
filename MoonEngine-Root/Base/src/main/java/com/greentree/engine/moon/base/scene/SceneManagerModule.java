package com.greentree.engine.moon.base.scene;

import com.greentree.engine.moon.base.modules.CreateProperty;
import com.greentree.engine.moon.base.modules.DestroyProperty;
import com.greentree.engine.moon.base.modules.WriteProperty;
import com.greentree.engine.moon.base.scene.base.SceneManagerBase;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.modules.UpdateModule;
import com.greentree.engine.moon.modules.property.EngineProperties;

public class SceneManagerModule implements LaunchModule, TerminateModule, UpdateModule {
	
	private SceneManagerBase manager;
	
	@WriteProperty({SceneManagerProperty.class})
	@Override
	public void update() {
		manager.update();
	}
	
	@DestroyProperty({SceneManagerProperty.class})
	@Override
	public void terminate() {
		manager.clearScene();
	}
	
	@CreateProperty({SceneManagerProperty.class})
	@Override
	public void launch(EngineProperties properties) {
		manager = new SceneManagerBase(properties);
		properties.add(new SceneManagerProperty(manager));
	}
	
}
