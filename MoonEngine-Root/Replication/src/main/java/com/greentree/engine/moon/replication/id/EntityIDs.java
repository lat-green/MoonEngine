package com.greentree.engine.moon.replication.id;

import java.util.HashMap;
import java.util.Map;

import com.greentree.commons.util.collection.MapID;
import com.greentree.commons.util.collection.MapIDImpl;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.WorldComponent;


public final class EntityIDs implements WorldComponent {
	private static final long serialVersionUID = 1L;

	private final Map<Integer, Entity> ids = new HashMap<>();
	private final MapID f = new MapIDImpl();
	
	public boolean contains(int id) {
		return ids.containsKey(id);
	}
	
	public Entity get(int id) {
		if(!ids.containsKey(id))
			throw new IllegalArgumentException("not entity " + id);
		return ids.get(id);
	}
	
	void addID(Entity e) {
		final var id = f.get();
		e.add(new EntityID(id));
	}
	
	void add(Entity e) {
		final var id = e.get(EntityID.class).id();
		if(ids.containsKey(id))
			throw new IllegalArgumentException("already add " + ids.get(id) + " " + e);
		ids.put(id, e);
	}

	void remove(Entity e) {
		final var id = e.get(EntityID.class).id();
		ids.remove(id);
		f.free(id);
	}

	public int getFree() {
		return 0;
	}
	
}
