package com.greentree.common.ecs.system;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.annotation.WriteComponent;

public class BSystem implements InitSystem {
	
	@WriteComponent({TestComponent1.class})
	@Override
	public void init(World world) {
		
	}
	
	@Override
	public String toString() {
		return "B";
	}
}
