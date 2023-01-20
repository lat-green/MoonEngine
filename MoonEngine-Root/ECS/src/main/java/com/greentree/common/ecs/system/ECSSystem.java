package com.greentree.common.ecs.system;

public sealed interface ECSSystem permits InitSystem, UpdateSystem, DestroySystem {
	
}
