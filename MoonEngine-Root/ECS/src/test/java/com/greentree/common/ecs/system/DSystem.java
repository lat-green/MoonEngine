package com.greentree.common.ecs.system;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.annotation.DestroyComponent;

public class DSystem implements InitSystem {
	
	@DestroyComponent({TestComponent1.class})
	@Override
	public void init(World world) {
		
	}
	
	@Override
	public String toString() {
		return "D";
	}
	
}
