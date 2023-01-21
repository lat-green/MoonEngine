package com.greentree.engine.moon.render.scene;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.CreateWorldComponent;
import com.greentree.engine.moon.ecs.annotation.ReadWorldComponent;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
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
