package com.greentree.engine.moon.base.scene;

import com.greentree.engine.moon.ecs.system.ECSSystem;

public interface SceneManager {
	
	void set(Scene scene);
	void addGlobalSystem(ECSSystem system);
	
}
