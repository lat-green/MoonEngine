package com.greentree.engine.moon.demo1;

import com.greentree.engine.moon.base.systems.ReadWorldComponent;
import com.greentree.engine.moon.base.time.Time;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class LogFPSSystem implements InitSystem, UpdateSystem {
	
	private static final float UPDATE_TIME = 2f;
	private Time time;
	private float t;
	private int fps;
	
	
	
	@ReadWorldComponent({Time.class})
	@Override
	public void update() {
		t += time.delta();
		fps++;
		while(t > UPDATE_TIME) {
			t -= UPDATE_TIME;
			System.out.println(fps / UPDATE_TIME);
			fps = 0;
		}
	}
	
	@ReadWorldComponent({Time.class})
	@Override
	public void init(World world) {
		time = world.get(Time.class);
	}
	
	
}
