package com.greentree.engine.moon.signals.controller;

import org.joml.Matrix3f;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.annotation.ReadComponent;
import com.greentree.common.ecs.annotation.ReadWorldComponent;
import com.greentree.common.ecs.annotation.WriteComponent;
import com.greentree.common.ecs.filter.Filter;
import com.greentree.common.ecs.filter.FilterBuilder;
import com.greentree.common.ecs.system.DestroySystem;
import com.greentree.common.ecs.system.InitSystem;
import com.greentree.common.ecs.system.UpdateSystem;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.base.time.Time;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.signals.device.DevicesComponent;

public class Controller3DSystem implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final FilterBuilder builder = new FilterBuilder().required(Controller3D.class)
			.required(DevicesComponent.class);
	
	
	private Filter filter;
	
	private Time time;
	
	@Override
	public void destroy() {
		filter.close();
		filter = null;
		time = null;
	}
	
	@ReadWorldComponent({Time.class})
	@Override
	public void init(World world) {
		time = world.get(Time.class);
		filter = builder.build(world);
	}
	
	@WriteComponent({Transform.class})
	@ReadComponent({Controller3D.class,DevicesComponent.class})
	@Override
	public void update() {
		for(var c : filter) {
			final var co = c.get(Controller3D.class);
			final var t = c.get(Transform.class);
			final var input = c.get(DevicesComponent.class).signals();
			
			final var look = new Vector2f(input.getValue(new LookX()).value(),
					input.getValue(new LookY()).value());
			final var move = new Vector3f(input.getValue(new MoveX()).value(),
					input.getValue(new MoveY()).value(), input.getValue(new MoveZ()).value());
			
			t.rotation.identity();
			t.rotation.rotateY(-look.x());
			t.rotation.rotateX(-look.y());
			
			var xz = t.direction().xz().normalize();
			
			var mat = new Matrix3f(-xz.y(), 0, xz.x(), 0, 1, 0, xz.x(), 0, xz.y());
			t.position.add(move.mul(mat).mul(co.speed() * time.delta()));
		}
	}
	
}
