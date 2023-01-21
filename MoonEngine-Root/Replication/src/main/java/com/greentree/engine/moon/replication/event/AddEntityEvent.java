package com.greentree.engine.moon.replication.event;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.replication.id.EntityID;
import com.greentree.engine.moon.replication.id.EntityIDs;

public record AddEntityEvent(int entity) implements EntityEvent {

	@Override
	public void accept(World world) {
		if(!world.get(EntityIDs.class).contains(entity)) {
        	final var e = world.newEntity();
        	e.add(new EntityID(entity));
		}
	}

}
