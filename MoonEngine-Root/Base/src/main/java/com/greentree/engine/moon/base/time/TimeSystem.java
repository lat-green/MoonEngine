package com.greentree.engine.moon.base.time;

import com.greentree.engine.moon.base.property.world.CreateWorldComponent;
import com.greentree.engine.moon.base.property.world.WriteWorldComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class TimeSystem implements InitSystem, UpdateSystem {
	
	private Time time;
	
	@WriteWorldComponent({Time.class})
	@Override
	public void update() {
		time.step();
	}
	
	@CreateWorldComponent({Time.class})
	@Override
	public void init(World world) {
		this.time = new Time();
		world.add(time);
	}
	
	
}
