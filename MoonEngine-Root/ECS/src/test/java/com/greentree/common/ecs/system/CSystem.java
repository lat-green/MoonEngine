package com.greentree.common.ecs.system;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.annotation.ReadComponent;

public class CSystem implements InitSystem {
	
	@ReadComponent({TestComponent1.class})
	@Override
	public void init(World world) {
		
	}
	
	@Override
	public String toString() {
		return "C";
	}
}
