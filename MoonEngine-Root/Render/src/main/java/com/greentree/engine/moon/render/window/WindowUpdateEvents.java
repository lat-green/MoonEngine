
package com.greentree.engine.moon.render.window;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.ReadWorldComponent;
import com.greentree.engine.moon.ecs.annotation.WriteWorldComponent;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class WindowUpdateEvents implements InitSystem, DestroySystem, UpdateSystem {
	
	private WindowLibrary library;
	
	@ReadWorldComponent({WindowLibraryProperty.class})
	@Override
	public void init(World world) {
		library = world.get(WindowLibraryProperty.class).library();
	}
	
	@WriteWorldComponent({WindowLibraryProperty.class})
	@Override
	public void update() {
		library.updateEvents();
	}
	
	@Override
	public void destroy() {
		library = null;
	}
	
}
