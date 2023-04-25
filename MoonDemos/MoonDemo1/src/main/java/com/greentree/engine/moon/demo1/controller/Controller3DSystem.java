package com.greentree.engine.moon.demo1.controller;

import org.joml.Matrix3f;

import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.math.vector.Vector3f;
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

public class Controller3DSystem implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final FilterBuilder BUILDER = new FilterBuilder()
			.required(Controller3D.class);
	
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
	@ReadComponent({Controller3D.class})
	@Override
	public void update() {
		for(var c : filter) {
			final var co = c.get(Controller3D.class);
			final var t = c.get(Transform.class);
			
			final var look = new Vector2f(input.getValue(PlayerInput.LookX).value(),
					input.getValue(PlayerInput.LookY).value());
			final var move = new Vector3f(input.getValue(PlayerInput.MoveX).value(),
					input.getValue(PlayerInput.MoveY).value(), input.getValue(PlayerInput.MoveZ).value());
			
			t.rotation.identity();
			t.rotation.rotateY(-look.x());
			t.rotation.rotateX(-look.y());
			
			var xz = t.direction().xz().normalize();
			
			var mat = new Matrix3f(-xz.y(), 0, xz.x(), 0, 1, 0, xz.x(), 0, xz.y());
			t.position.add(move.mul(mat).mul(co.speed() * time.delta()));
		}
	}
	
}
