package com.greentree.common.ecs.system;

import com.greentree.common.ecs.World;

public non-sealed interface InitSystem extends ECSSystem {
	
	void init(World world);
	
}
