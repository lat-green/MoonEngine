package com.greentree.engine.moon.render.scene;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.annotation.CreateWorldComponent;
import com.greentree.common.ecs.annotation.ReadWorldComponent;
import com.greentree.common.ecs.system.DestroySystem;
import com.greentree.common.ecs.system.InitSystem;
import com.greentree.engine.moon.render.camera.Cameras;


public final class RenderSceneSystem implements InitSystem, DestroySystem {
	
	private MoonSceneContext context;
	
	@Override
	public void destroy() {
		context.close();
	}
	
	@ReadWorldComponent({Cameras.class})
	@CreateWorldComponent({RenderSceneWorldComponent.class})
	@Override
	public void init(World world) {
		context = new MoonSceneContext(world);
		world.add(new RenderSceneWorldComponent(context));
		
	}
	
}
