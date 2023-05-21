package com.greentree.engine.moon.replication.id;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.engine.moon.base.systems.CreateWorldComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;

public class EntityIDsSystem implements InitSystem, DestroySystem {
	
	private EntityIDs ids;
	private ListenerCloser lc1, lc2;
	
	@Override
	public void destroy() {
		lc1.close();
		lc2.close();
	}
	
	@CreateWorldComponent({EntityIDs.class})
	@Override
	public void init(World world) {
		ids = new EntityIDs();
		world.add(ids);
		lc1 = world.onAddComponent(EntityID.class, e-> {
			ids.add(e);
		});
		lc2 = world.onRemoveComponent(EntityID.class, e-> {
			ids.remove(e);
		});
	}
	
	
	
}
