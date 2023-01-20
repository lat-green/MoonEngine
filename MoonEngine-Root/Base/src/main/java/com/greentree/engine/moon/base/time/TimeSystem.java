package com.greentree.engine.moon.base.time;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.annotation.CreateWorldComponent;
import com.greentree.common.ecs.system.InitSystem;
import com.greentree.common.ecs.system.UpdateSystem;

public class TimeSystem implements InitSystem, UpdateSystem {
	
	private Time time;
	
	@Override
	public void update() {
		time.step();
	}
	
	@CreateWorldComponent({Time.class})
	@Override
	public void init(World world) {
		this.time = new Time();
		world.add(time);
		time.step();
	}
	
	
}
