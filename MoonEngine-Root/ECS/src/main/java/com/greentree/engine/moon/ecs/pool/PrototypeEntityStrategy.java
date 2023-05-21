package com.greentree.engine.moon.ecs.pool;

import java.util.Comparator;

import com.greentree.engine.moon.ecs.Entity;

public class PrototypeEntityStrategy implements EntityPoolStrategy {
	private static final long serialVersionUID = 1L;
	
	private static final int REMOVE_COMPONENT_PRICE = 1;
	private static final int ADD_COMPONENT_PRICE = 2;
	

	private final Comparator<Entity> COMPARATOR;
	private final Entity prototype;

	public PrototypeEntityStrategy(Entity prototype) {
		this.prototype = prototype;
		COMPARATOR = Comparator.comparing(e -> {
			int res = 0;
			for(var c : e)
				if(!prototype.contains(c.getClass()))
					res += REMOVE_COMPONENT_PRICE;
			for(var c : prototype)
				if(!e.contains(c.getClass()))
					res += ADD_COMPONENT_PRICE;
			return res;
		});
	}

	@Override
	public Comparator<Entity> comporator() {
		return COMPARATOR;
	}

	@Override
	public void toInstance(Entity entity) {
		try(final var lock = entity.lock()) {
    		for(var v : entity) {
    			final var cls = v.getClass();
    			if(!prototype.contains(cls))
    				lock.remove(cls);
    		}
		}
		prototype.copyTo(entity);
	}

}
