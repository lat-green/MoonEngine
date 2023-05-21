package com.greentree.engine.moon.replication.id;

import com.greentree.engine.moon.base.systems.ReadWorldComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.replication.ReplicationComponent;

public class EntityIDGenerator implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final FilterBuilder CREATE_ID = new FilterBuilder().required(ReplicationComponent.class).ignore(EntityID.class);
	
	private EntityIDs ids;
	private Filter createID;
	
	@Override
	public void destroy() {
		createID.close();
		createID = null;
	}
	
	@Override
	public void update() {
		for(var e : createID)
			ids.addID(e);
	}
	
	@ReadWorldComponent({EntityIDs.class})
	@Override
	public void init(World world) {
		createID = CREATE_ID.build(world);
		ids = world.get(EntityIDs.class);
	}
	
	
	
}
