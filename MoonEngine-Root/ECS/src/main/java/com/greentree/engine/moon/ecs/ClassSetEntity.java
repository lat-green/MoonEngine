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

public final class ClassSetEntity implements Entity, Externalizable, Cloneable {
	
	
	private final ClassSet<Component> components = new ComponentClassSet();
	
	public ClassSetEntity() {
	}
	
	@Override
	public void clear() {
		components.clear();
	}
	
	@Override
	public ClassSetEntity copy() {
		final var clone = new ClassSetEntity();
		copyTo(clone);
		return clone;
	}
	
	@Override
	public boolean contains(Class<? extends Component> componentClass) {
		return components.contains(componentClass);
	}
	
	@Override
	public boolean contains(Component component) {
		return components.contains(component);
	}
	
	@Override
	public <T extends Component> T get(Class<T> componentClass) {
		return components.get(componentClass);
	}
	
	@Override
	public TypedObjectObservable<? extends Class<? extends Component>, ? extends Component> getAddAction() {
		return components.getAddAction();
	}
	
	@Override
	public TypedObjectObservable<? extends Class<? extends Component>, ? extends Component> getRemoveAction() {
		return components.getRemoveAction();
	}
	
	@Override
	public boolean isEmpty() {
		return components.isEmpty();
	}
	
	@Override
	public Iterator<Component> iterator() {
		return components.iterator();
	}
	
	@Override
	public LockClassSet<Component> lock() {
		return components.lock();
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
	
	@Override
	public int size() {
		return components.size();
	}
	
	
	@Override
	public String toString() {
		return "Entity " + IteratorUtil.toString(components);
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(components.size());
		for(var c : this)
			out.writeObject(c);
	}
	
	
	public static final class ComponentClassSet extends ClassSet<Component> {
		
		private static final long serialVersionUID = 1L;
		
		public ComponentClassSet() {
		}
		
		@Override
		protected Iterable<? extends Class<? extends Component>> getClassRequired(
				Class<? extends Component> cls) {
			return AnnotationUtil.getRequiredComponent(cls);
		}
		
	}
	
}
