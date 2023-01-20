package com.greentree.engine.moon.opengl.render.camera;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.annotation.CreateComponent;
import com.greentree.common.ecs.annotation.ReadComponent;
import com.greentree.common.ecs.filter.Filter;
import com.greentree.common.ecs.filter.FilterBuilder;
import com.greentree.common.ecs.system.DestroySystem;
import com.greentree.common.ecs.system.InitSystem;
import com.greentree.common.ecs.system.UpdateSystem;
import com.greentree.engine.moon.render.camera.CameraComponent;

public class CameraTargetGeneratedSystem implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final FilterBuilder CAMERAS = new FilterBuilder().required(CameraComponent.class).ignore(CameraTarget.class);
	private Filter cameras;
	
	public CameraTargetGeneratedSystem() {
		super();
	}
	
	@Override
	public void init(World world) {
		cameras = CAMERAS.build(world);
	}
	
	@Override
	public void destroy() {
		cameras.close();
		cameras = null;
	}
	
	@ReadComponent({CameraComponent.class})
	@CreateComponent({CameraTarget.class})
	@Override
	public void update() {
		for(var e : cameras) {
			final var c = e.get(CameraComponent.class);
			final var target = new CameraTarget(c.width(), c.height());
			e.add(target);
		}
	}
	
}
