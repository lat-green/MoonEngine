package com.greentree.engine.moon.base.transform;


import com.greentree.common.ecs.World;
import com.greentree.common.ecs.filter.Filter;
import com.greentree.common.ecs.filter.FilterBuilder;
import com.greentree.common.ecs.system.DestroySystem;
import com.greentree.common.ecs.system.InitSystem;
import com.greentree.common.ecs.system.UpdateSystem;
import com.greentree.commons.math.vector.Vector3f;

public final class LookAtSystem implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final FilterBuilder LOOK_AT = new FilterBuilder().required(LookAt.class);
	
	private Filter look_at;
	
	@Override
	public void destroy() {
		look_at.close();
		look_at = null;
	}
	
	@Override
	public void update() {
		final var joml_up = Transform.UP.toJoml();
		final var joml_temp = new org.joml.Vector3f();
		final var temp = new Vector3f();
		for(var e : look_at) {
			final var t = e.get(Transform.class);
			final var l = e.get(LookAt.class).vec();
			
			l.sub(t.position, temp);
			
			joml_temp.set(temp.x, temp.y, temp.z);
			
			t.rotation.lookAlong(joml_temp, joml_up);
		}
	}
	
	@Override
	public void init(World world) {
		look_at = LOOK_AT.build(world);
	}
}
