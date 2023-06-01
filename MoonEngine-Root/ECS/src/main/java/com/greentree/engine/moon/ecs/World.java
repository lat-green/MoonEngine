package com.greentree.engine.moon.ecs;

import java.io.Serializable;
import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.TypedObjectObservable;
import com.greentree.engine.moon.ecs.component.Component;
import com.greentree.engine.moon.ecs.filter.WorldFilterBuilder;
import com.greentree.engine.moon.ecs.filter.WorldFilterBuilderImpl;

public interface World extends Iterable<Entity>, Serializable {
	
	TypedObjectObservable<Class<? extends Component>, Entity> getAddComponentAction();
	
	TypedObjectObservable<Class<? extends Component>, Entity> getRemoveComponentAction();
	
	<T extends Component> ListenerCloser onAddComponent(Class<T> componentClass, Consumer<? super Entity> l);
	
	ListenerCloser onAddComponent(Consumer<? super Entity> l);
	
	ListenerCloser onAddEntity(Consumer<? super Entity> l);
	
	<T extends Component> ListenerCloser onRemoveComponent(Class<T> componentClass, Consumer<? super Entity> l);
	
	ListenerCloser onRemoveComponent(Consumer<? super Entity> l);
	ListenerCloser onRemoveEntity(Consumer<? super Entity> l);
	
	
	default WorldFilterBuilder newFilter() {
		return new WorldFilterBuilderImpl(this);
	}
	
	void active(Entity entity);
	
	@Deprecated
	void add(WorldComponent component);
	
	void clear();
	
	@Deprecated
	Iterable<? extends WorldComponent> components();
	
	@Deprecated
	boolean contains(Class<? extends WorldComponent> componentClass);
	
	void deactivate(Entity entity);
	
	void deleteEntity(Entity entity);
	@Deprecated
	<T extends WorldComponent> T get(Class<T> componentClass);
	
	boolean isActive(Entity entity);
	
	boolean isDeactive(Entity entity);
	
	Entity newDeactiveEntity();
	
	Entity newEntity();
	
	int size();
	
}
