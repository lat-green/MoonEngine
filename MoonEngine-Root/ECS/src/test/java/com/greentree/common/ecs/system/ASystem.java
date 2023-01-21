package com.greentree.common.ecs.system;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.CreateComponent;
import com.greentree.engine.moon.ecs.system.InitSystem;

public class ASystem implements InitSystem {
	
	@CreateComponent({TestComponent1.class})
	@Override
	public void init(World world) {
		
	}
	
	@Override
	public String toString() {
		return "A";
	}
	
}
