package com.greentree.engine.moon.render.camera;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.annotation.CreateWorldComponent;
import com.greentree.common.ecs.annotation.DestroyWorldComponent;
import com.greentree.common.ecs.system.DestroySystem;
import com.greentree.common.ecs.system.InitSystem;
import com.greentree.commons.action.ListenerCloser;

public class CameraSystem implements InitSystem, DestroySystem {
	
	private Cameras cameras;
	
	@CreateWorldComponent({Cameras.class})
	@Override
	public void init(World world) {
		cameras = new Cameras();
		world.add(cameras);
		
		setMainCamera(world);
		
		world.onRemoveComponent(CameraComponent.class, e-> {
			if(e.equals(cameras.main()))
				setMainCamera(world);
		});
		
	}
	
	private void setMainCamera(World world) {
		for(var e : world)
			if(e.contains(CameraComponent.class))
				cameras.setMainCamera(e);
			
		final var lc = new ListenerCloser[1];
		lc[0] = world.onAddComponent(CameraComponent.class, e-> {
			cameras.setMainCamera(e);
			lc[0].close();
		});
		
	}
	
	@DestroyWorldComponent({Cameras.class})
	@Override
	public void destroy() {
		cameras.setMainCamera(null);
		cameras = null;
	}
	
}
