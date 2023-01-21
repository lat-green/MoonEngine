package com.greentree.engine.moon.base.scene;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.ECSSystem;

public interface Scene {
	
	void build(World world);
	ECSSystem getSystems(Iterable<? extends ECSSystem> globalSystems);
	
}
