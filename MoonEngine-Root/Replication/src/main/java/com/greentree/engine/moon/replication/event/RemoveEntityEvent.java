package com.greentree.engine.moon.replication.event;

import com.greentree.common.ecs.World;
import com.greentree.engine.moon.replication.id.EntityIDs;

public record RemoveEntityEvent(int entity) implements EntityEvent {

	@Override
	public void accept(World world) {
		final var e = world.get(EntityIDs.class).get(entity);
		world.deleteEntity(e);
	}

}
