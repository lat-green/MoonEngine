package com.greentree.common.ecs.system;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.annotation.CreateComponent;

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
