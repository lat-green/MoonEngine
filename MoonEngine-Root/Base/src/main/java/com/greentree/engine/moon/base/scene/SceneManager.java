package com.greentree.engine.moon.base.scene;

import com.greentree.common.ecs.system.ECSSystem;

public interface SceneManager {
	
	void set(Scene scene);
	void addGlobalSystem(ECSSystem system);
	
}
