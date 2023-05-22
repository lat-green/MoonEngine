package com.greentree.engine.moon.base.name;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observer.object.EventAction;
import com.greentree.commons.action.observer.type.TypedObjectAction;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.base.component.CreateSystem;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.WorldComponent;

@CreateSystem(NameSystem.class)
public class Names implements WorldComponent, Iterable<String> {
	
	private static final long serialVersionUID = 1L;
	
	private transient final TypedObjectAction<String, Entity> addEntityAction = new TypedObjectAction<>();
	private transient final TypedObjectAction<String, Entity> removeEntityAction = new TypedObjectAction<>();
	private transient final EventAction<String> addAction = new EventAction<>();
	private transient final EventAction<String> removeAction = new EventAction<>();
	
	public ListenerCloser onAddEntity(String layer, Consumer<? super Entity> listener) {
		return addEntityAction.addListener(layer, listener);
	}
	
	public ListenerCloser onRemoveEntity(String layer, Consumer<? super Entity> listener) {
		return removeEntityAction.addListener(layer, listener);
	}
	
	public ListenerCloser onAddLayer(Consumer<? super String> listener) {
		return addAction.addListener(listener);
	}
	
	public ListenerCloser onRemoveLayer(Consumer<? super String> listener) {
		return removeAction.addListener(listener);
	}
	
	private final Map<String, Collection<Entity>> names = new HashMap<>();
	
	void add(Entity entity) {
		final var layer = entity.get(Name.class).value();
		final Collection<Entity> entities;
		if(names.containsKey(layer)) {
			entities = names.get(layer);
		}else {
			entities = new ArrayList<>();
			names.put(layer, entities);
			addAction.event(layer);
		}
		entities.add(entity);
		addEntityAction.event(layer, entity);
	}
	
	void remove(Entity entity) {
		final var layer = entity.get(Name.class).value();
		final Collection<Entity> entities = names.get(layer);
		entities.remove(entity);
		removeEntityAction.event(layer, entity);
		if(entities.isEmpty()) {
			removeAction.event(layer);
			names.remove(layer);
		}
	}
	
	@Override
	public Iterator<String> iterator() {
		return names.keySet().iterator();
	}
	
	public boolean has(String name) {
		return names.containsKey(name);
	}
	
	public Iterable<? extends Entity> getAll(String name) {
		if(!names.containsKey(name))
			return IteratorUtil.empty();
		return names.get(name);
	}
	
	public Entity get(String name) {
		final var all = getAll(name).iterator();
		if(all.hasNext())
			return all.next();
		return null;
	}
	
}
