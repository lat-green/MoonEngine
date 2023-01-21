package com.greentree.engine.moon.ecs.system;

public sealed interface ECSSystem permits InitSystem, UpdateSystem, DestroySystem {
	
}
