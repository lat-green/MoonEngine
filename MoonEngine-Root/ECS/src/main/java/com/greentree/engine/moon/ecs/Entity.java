package com.greentree.engine.moon.ecs;

import com.greentree.commons.action.observable.TypedObjectObservable;
import com.greentree.commons.util.iterator.SizedIterable;
import com.greentree.engine.moon.ecs.component.Component;

public interface Entity extends SizedIterable<Component> {
	
	default void add(Component component) {
		try(final var lock = lock()) {
			lock.add(component);
		}
	}
	
	void clear();
	
	Entity copy();
	
	default Entity copy(World world) {
		final var copy = world.newEntity();
		copyTo(copy);
		return copy;
	}
	
	default void copyTo(Entity clone) {
		try(var lock = clone.lock()) {
			for(var c : clone) {
				final var cls = c.getClass();
				if(!contains(cls))
					lock.remove(c.getClass());
			}
		}
		try(var lock = clone.lock()) {
			for(var c : this) {
				final var cls = c.getClass();
				if(clone.contains(cls))
					if(!c.copyTo(clone.get(cls)))
						lock.remove(cls);
			}
		}
		try(var lock = clone.lock()) {
			for(var c : this) {
				final var cls = c.getClass();
				if(!clone.contains(cls))
					lock.add(c.copy());
			}
		}
	}
	
	boolean contains(Class<? extends Component> componentClass);
	
	boolean contains(Component component);
	
	<T extends Component> T get(Class<T> componentClass);
	
	TypedObjectObservable<? extends Class<? extends Component>, ? extends Component> getAddAction();
	
	TypedObjectObservable<? extends Class<? extends Component>, ? extends Component> getRemoveAction();
	
	boolean isEmpty();
	
	ComponentLock lock();
	
	default void remove(Class<? extends Component> componentClass) {
		try(final var lock = lock()) {
			lock.remove(componentClass);
		}
	}
	
}
