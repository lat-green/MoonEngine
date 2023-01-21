package com.greentree.engine.moon.ecs;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;

import com.greentree.commons.action.observable.TypedObjectObservable;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.ecs.ClassSet.LockClassSet;
import com.greentree.engine.moon.ecs.annotation.AnnotationUtil;
import com.greentree.engine.moon.ecs.component.Component;

public final class Entity implements Iterable<Component>, Externalizable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	private final ClassSet<Component> components = new ComponentClassSet();

	public static final class ComponentClassSet extends ClassSet<Component> {
		private static final long serialVersionUID = 1L;
		
		public ComponentClassSet() {
		}
		
		protected Iterable<? extends Class<? extends Component>> getClassRequired(Class<? extends Component> cls) {
			return AnnotationUtil.getRequiredComponent(cls);
		};
		
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(components.size());
		for(var c : this)
			out.writeObject(c);
	}

	
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		var size = in.readInt();
		try(final var lock = lock()) {
			while(size-- > 0) {
				final var c = (Component) in.readObject();
				lock.add(c);
			}
		}
	}
	
	public Entity() {
	}

	public void clear() {
		components.clear();
	}

	@Override
	public Entity clone() {
		final var clone = new Entity();
		cloneTo(clone);
		return clone;
	}

	public Entity clone(World world) {
		final var clone = world.newEntity();
		cloneTo(clone);
		return clone;
	}

	public void cloneTo(Entity clone) {
		try(var lock = clone.lock()) {
			for(var c : clone) {
				final var cls = c.getClass();
				if(!contains(cls))
					lock.remove(c.getClass());
			}
		}
		try(var lock = clone.lock()) {
			for(var c : components) {
				final var cls = c.getClass();
				if(clone.contains(cls))
					if(!c.copyTo(clone.get(cls)))
						lock.remove(cls);
			}
		}
		try(var lock = clone.lock()) {
			for(var c : components) {
				final var cls = c.getClass();
				if(!clone.contains(cls))
					lock.add(c.copy());
			}
		}
	}

	public boolean contains(Class<? extends Component> componentClass) {
		return components.contains(componentClass);
	}

	public boolean contains(Component component) {
		return components.contains(component);
	}

	public <T extends Component> T get(Class<T> componentClass) {
		return components.get(componentClass);
	}

	public boolean isEmpty() {
		return components.isEmpty();
	}

	@Override
	public Iterator<Component> iterator() {
		return components.iterator();
	}

	public LockClassSet<Component> lock() {
		return components.lock();
	}

	public TypedObjectObservable<? extends Class<? extends Component>, ? extends Component> getAddAction() {
		return components.getAddAction();
	}
	public TypedObjectObservable<? extends Class<? extends Component>, ? extends Component> getRemoveAction() {
		return components.getRemoveAction();
	}

	public int size() {
		return components.size();
	}

	@Override
	public String toString() {
		return "Entity " + IteratorUtil.toString(components);
	}


	public void add(Component component) {
		try(final var lock = lock()) {
			lock.add(component);
		}
	}

	public void remove(Class<? extends Component> componentClass) {
		try(final var lock = lock()) {
			lock.remove(componentClass);
		}
	}

}
