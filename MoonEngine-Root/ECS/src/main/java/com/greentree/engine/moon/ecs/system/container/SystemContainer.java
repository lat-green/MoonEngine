package com.greentree.engine.moon.ecs.system.container;

import java.util.Collection;

import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.ECSSystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public interface SystemContainer {
	
	Collection<? extends InitSystem> initSystems();
	Collection<? extends DestroySystem> destroySystems();
	Collection<? extends UpdateSystem> updateSystems();
	
	SystemController add(ECSSystem system);
	SystemController get(Class<? extends ECSSystem> cls);
	
}
