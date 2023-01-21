package com.greentree.engine.moon.ecs.system;

import com.greentree.engine.moon.ecs.World;

public non-sealed interface InitSystem extends ECSSystem {
	
	void init(World world);
	
}
