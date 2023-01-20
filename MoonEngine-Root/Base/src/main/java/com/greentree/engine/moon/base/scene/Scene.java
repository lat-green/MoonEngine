package com.greentree.engine.moon.base.scene;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.system.ECSSystem;

public interface Scene {
	
	void build(World world);
	ECSSystem getSystems(Iterable<? extends ECSSystem> globalSystems);
	
}
