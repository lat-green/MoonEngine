package com.greentree.engine.moon.ecs.system.container;

import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.engine.moon.ecs.system.ECSSystem;

public interface SystemsRegistry {
	
	default void add(String className) throws ClassNotFoundException {
		add(ClassUtil.loadClassInAllPackages(ECSSystem.class, className));
	}
	
	void add(Class<? extends ECSSystem> cls);
	
	
	
}
