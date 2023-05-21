package com.greentree.engine.moon.base.layer;

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
import com.greentree.engine.moon.base.systems.CreateSystem;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.WorldComponent;

@CreateSystem(LayerSystem.class)
public class Layers implements WorldComponent, Iterable<String> {
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
	
	private final Map<String, Collection<Entity>> layers = new HashMap<>();

	void add(Entity entity) {
		final var layer = entity.get(Layer.class).value();
		final Collection<Entity> entities;
		if(layers.containsKey(layer)) {
			entities = layers.get(layer);
		}else {
			entities = new ArrayList<>();
			layers.put(layer, entities);
			addAction.event(layer);
		}
		entities.add(entity);
		addEntityAction.event(layer, entity);
	}
	
	void remove(Entity entity) {
		final var layer = entity.get(Layer.class).value();
		final Collection<Entity> entities = layers.get(layer);
		entities.remove(entity);
		removeEntityAction.event(layer, entity);
		if(entities.isEmpty()) {
			removeAction.event(layer);
			layers.remove(layer);
		}
	}

	@Override
	public Iterator<String> iterator() {
		return layers.keySet().iterator();
	}

	public boolean has(String layer) {
		return layers.containsKey(layer);
	}
	public Iterable<? extends Entity> get(String layer) {
		if(!layers.containsKey(layer))
			return IteratorUtil.empty();
		return layers.get(layer);
	}
	
}
