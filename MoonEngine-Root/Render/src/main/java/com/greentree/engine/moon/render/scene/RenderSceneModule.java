package com.greentree.engine.moon.render.scene;

import com.greentree.engine.moon.base.scene.SceneManagerProperty;
import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.annotation.WriteProperty;


public class RenderSceneModule implements LaunchModule {
	
	@WriteProperty({SceneManagerProperty.class})
	@Override
	public void launch(EngineProperties properties) {
		properties.get(SceneManagerProperty.class).manager().addGlobalSystem(new RenderSceneSystem());
	}
	
}
