package com.greentree.engine.moon.monkey.input;

import com.greentree.engine.moon.base.time.Time;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.ReadComponent;
import com.greentree.engine.moon.ecs.annotation.ReadWorldComponent;
import com.greentree.engine.moon.ecs.annotation.WriteComponent;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.device.Devices;

public class ControllerSystem implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final FilterBuilder BUILDER = new FilterBuilder()
			.required(Controller.class);
	
	private Filter filter;
	
	private Time time;
	
	private Devices input;
	
	@Override
	public void destroy() {
		filter.close();
		filter = null;
		time = null;
		input = null;
	}
	
	@ReadWorldComponent({Time.class})
	@Override
	public void init(World world) {
		filter = BUILDER.build(world);
		time = world.get(Time.class);
		input = world.get(DevicesProperty.class).devices();
	}

	
	@ReadWorldComponent({Time.class})
	@WriteComponent({Transform.class})
	@ReadComponent({Controller.class})
	@Override
	public void update() {
		for(var c : filter) {
			final var co = c.get(Controller.class);
			final var t = c.get(Transform.class);
			
			var move = input.get(PlayerInput.Move);
			var jump = input.get(PlayerButton.Jump);
			
			
		}
	}
	
}
