package com.greentree.engine.moon.base.transform;


import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.Use;
import com.greentree.engine.moon.ecs.annotation.UseStage;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public final class LookAtSystem implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final FilterBuilder LOOK_AT = new FilterBuilder().required(LookAt.class);
	
	private Filter look_at;
	
	@Override
	public void destroy() {
		look_at.close();
		look_at = null;
	}
	
	@Use(value = Transform.class, state = UseStage.WRITE)
	@Use(value = LookAt.class, state = UseStage.READ)
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
			
			t.rotation.identity();
			t.rotation.lookAlong(joml_temp, joml_up);
		}
	}
	
	@Override
	public void init(World world) {
		look_at = LOOK_AT.build(world);
	}
}
