package com.greentree.engine.moon.ecs;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.TypedObjectObservable;
import com.greentree.commons.action.observer.object.EventAction;
import com.greentree.commons.action.observer.type.TypedObjectAction;
import com.greentree.engine.moon.ecs.component.Component;

public final class CollectionWorld implements World, Externalizable {
	
	private transient final TypedObjectAction<Class<? extends Component>, Entity> odAddComponentAction = new TypedObjectAction<>();
	private transient final TypedObjectAction<Class<? extends Component>, Entity> onRemoveComponentAction = new TypedObjectAction<>();
	private transient final EventAction<Entity> onAddEntityAction = new EventAction<>();
	private transient final EventAction<Entity> onRemoveEntityAction = new EventAction<>();
	
	private transient final ClassSet<WorldComponent> components = new ClassSet<>();
	
	private final Collection<Entity> activeEntities;
	private final Collection<Entity> deactiveEntities;
	
	private transient final Map<Entity, ListenerCloser> entityListeners;
	
	
	public CollectionWorld() {
		this(128);
	}
	
	public CollectionWorld(int initialCapacity) {
		this(initialCapacity * 3 / 4, initialCapacity * 1 / 4);
	}
	
	public CollectionWorld(int activeInitialCapacity, int deactiveInitialCapacity) {
		activeEntities = new HashSet<>(activeInitialCapacity);
		entityListeners = new HashMap<>(activeInitialCapacity);
		
		deactiveEntities = new HashSet<>(deactiveInitialCapacity);
	}
	
	
	@Override
	public void active(Entity entity) {
		if(isActive(entity))
			return;
		if(!isDeactive(entity))
			throw new IllegalArgumentException("active not deactive " + entity);
		enuble(entity);
	}
	
	@Override
	public void add(WorldComponent component) {
		try(final var lock = components.lock()) {
			lock.add(component);
		}
	}
	
	@Override
	public void clear() {
		for(var e : new ArrayList<>(activeEntities))
			deleteActiveEntity(e);
		for(var e : new ArrayList<>(deactiveEntities))
			deleteDeactiveEntity(e);
	}
	
	@Override
	public Iterable<? extends WorldComponent> components() {
		return components;
	}
	
	@Override
	public boolean contains(Class<? extends WorldComponent> componentClass) {
		return components.contains(componentClass);
	}
	
	@Override
	public void deactivate(Entity entity) {
		if(isDeactive(entity))
			return;
		if(!isActive(entity))
			throw new IllegalArgumentException("deactive not active " + entity);
		disable(entity);
	}
	
	@Override
	public void deleteEntity(Entity entity) {
		if(isActive(entity))
			deleteActiveEntity(entity);
		else
			if(isDeactive(entity))
				deleteDeactiveEntity(entity);
			else
				throw new IllegalArgumentException("not my Entity " + entity);
	}
	
	@Override
	public <T extends WorldComponent> T get(Class<T> componentClass) {
		return components.get(componentClass);
	}
	
	@Override
	public TypedObjectObservable<Class<? extends Component>, Entity> getAddComponentAction() {
		return odAddComponentAction;
	}
	
	@Override
	public TypedObjectObservable<Class<? extends Component>, Entity> getRemoveComponentAction() {
		return onRemoveComponentAction;
	}
	
	@Override
	public boolean isActive(Entity entity) {
		return activeEntities.contains(entity);
	}
	
	@Override
	public boolean isDeactive(Entity entity) {
		return deactiveEntities.contains(entity);
	}
	
	@Override
	public Iterator<Entity> iterator() {
		return activeEntities.iterator();
	}
	
	@Override
	public Entity newDeactiveEntity() {
		final var e = new ClassSetEntity();
		initDeactiveEmpty(e);
		return e;
	}
	
	@Override
	public Entity newEntity() {
		final var e = new ClassSetEntity();
		initActiveEmpty(e);
		return e;
	}
	
	@Override
	public <T extends Component> ListenerCloser onAddComponent(Class<T> componentClass,
			Consumer<? super Entity> l) {
		return odAddComponentAction.addListener(componentClass, l);
	}
	
	@Override
	public ListenerCloser onAddComponent(Consumer<? super Entity> l) {
		return odAddComponentAction.addListener(l);
	}
	
	@Override
	public ListenerCloser onAddEntity(Consumer<? super Entity> l) {
		return onAddEntityAction.addListener(l);
	}
	
	@Override
	public <T extends Component> ListenerCloser onRemoveComponent(Class<T> componentClass,
			Consumer<? super Entity> l) {
		return onRemoveComponentAction.addListener(componentClass, l);
	}
	
	@Override
	public ListenerCloser onRemoveComponent(Consumer<? super Entity> l) {
		return onRemoveComponentAction.addListener(l);
	}
	
	@Override
	public ListenerCloser onRemoveEntity(Consumer<? super Entity> l) {
		return onRemoveEntityAction.addListener(l);
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		int size;
		size = in.readInt();
		while(size-- > 0) {
			final var e = (Entity) in.readObject();
			initActiveNotEmpty(e);
		}
		size = in.readInt();
		while(size-- > 0) {
			final var e = (Entity) in.readObject();
			initDeactiveNotEmpty(e);
		}
	}
	
	@Override
	public int size() {
		return activeEntities.size();
	}
	
	@Override
	public String toString() {
		var builder = new StringBuilder();
		builder.append("ECSWorld [");
		builder.append(activeEntities);
		builder.append(deactiveEntities);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(activeEntities.size());
		for(var c : activeEntities)
			out.writeObject(c);
		out.writeInt(deactiveEntities.size());
		for(var c : deactiveEntities)
			out.writeObject(c);
	}
	
	
	private void deleteActiveEntity(Entity entity) {
		removeActive(entity);
	}
	
	private void deleteDeactiveEntity(Entity entity) {
		deactiveEntities.remove(entity);
	}
	
	private void disable(Entity entity) {
		removeActive(entity);
		deactiveEntities.add(entity);
	}
	
	private void enuble(Entity entity) {
		deactiveEntities.remove(entity);
		initActiveNotEmpty(entity);
	}
	
	private void initActiveEmpty(Entity entity) {
		final var lc1 = entity.getAddAction().addListener(c-> {
			odAddComponentAction.event(c.getClass(), entity);
		});
		final var lc2 = entity.getRemoveAction().addListener(c-> {
			onRemoveComponentAction.event(c.getClass(), entity);
		});
		entityListeners.put(entity, ()-> {
			lc1.close();
			lc2.close();
		});
		activeEntities.add(entity);
		onAddEntityAction.event(entity);
	}
	
	private void initActiveNotEmpty(Entity entity) {
		initActiveEmpty(entity);
		for(var c : entity)
			odAddComponentAction.event(c.getClass(), entity);
	}
	
	private void initDeactiveEmpty(Entity e) {
		deactiveEntities.add(e);
	}
	
	private void initDeactiveNotEmpty(Entity e) {
		deactiveEntities.add(e);
	}
	
	private void removeActive(Entity entity) {
		entityListeners.remove(entity).close();
		activeEntities.remove(entity);
		for(var c : entity)
			onRemoveComponentAction.event(c.getClass(), entity);
		onRemoveEntityAction.event(entity);
	}
	
}
