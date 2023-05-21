package com.greentree.engine.moon.render.camera;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.engine.moon.base.systems.CreateWorldComponent;
import com.greentree.engine.moon.base.systems.DestroyWorldComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;

public class CameraSystem implements InitSystem, DestroySystem {
	
	private Cameras cameras;
	private ListenerCloser lc;
	
	@CreateWorldComponent({Cameras.class})
	@Override
	public void init(World world) {
		cameras = new Cameras();
		world.add(cameras);
		
		setMainCamera(world);
		
		lc = world.onRemoveComponent(CameraComponent.class, e-> {
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
		lc.close();
		lc = null;
		cameras.setMainCamera(null);
		cameras = null;
	}
	
}
