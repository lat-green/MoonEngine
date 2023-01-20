package com.greentree.engine.moon.base.scene;

import com.greentree.engine.moon.base.scene.base.SceneManagerBase;
import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.TerminateModule;
import com.greentree.engine.moon.module.UpdateModule;
import com.greentree.engine.moon.module.annotation.CreateProperty;
import com.greentree.engine.moon.module.annotation.DestroyProperty;
import com.greentree.engine.moon.module.annotation.WriteProperty;

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
