package com.greentree.engine.moon.ecs;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

import com.greentree.commons.action.observable.TypedObjectObservable;
import com.greentree.commons.action.observer.type.TypedObjectAction;
import com.greentree.commons.util.collection.OneClassSet;
import com.greentree.commons.util.iterator.IteratorUtil;

@SuppressWarnings({"unchecked", "deprecation"})
public class ClassSet<E> implements Iterable<E>, Externalizable {

	private transient final TypedObjectAction<Class<? extends E>, E> addAction = new TypedObjectAction<>();
	private final OneClassSet<E> components = new OneClassSet<>();
	private transient final TypedObjectAction<Class<? extends E>, E> removeAction = new TypedObjectAction<>();

	public void clear() {
		try(var lock = lock()) {
			for(E c : components)
				lock.remove((Class<? extends E>) c.getClass());
		}
	}


	public boolean contains(Class<? extends E> componentClass) {
		return components.containsClass(componentClass);
	}

	public boolean contains(E component) {
		return components.contains(component);
	}


	@Override
	public String toString() {
		return "ClassSet " + components;
	}


	public <T extends E> T get(Class<T> componentClass) {
		Objects.requireNonNull(componentClass);
		if(!contains(componentClass))
			throw new IllegalArgumentException("componenet "+componentClass.getName()+" not contains in " + this);
		return components.get(componentClass);
	}

	public TypedObjectObservable<? extends Class<? extends E>, ? extends E> getAddAction() {
		return addAction;
	}

	public TypedObjectObservable<? extends Class<? extends E>, ? extends E> getRemoveAction() {
		return removeAction;
	}
	public boolean isEmpty() {
		return components.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return components.iterator();
	}

	public LockClassSet<E> lock() {
		return new LockClassSet<>(this);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		var size = in.readInt();
		try(final var lock = lock()) {
			while(size-- > 0) {
				final var c = (E) in.readObject();
				lock.add(c);
			}
		}
	}
	public int size() {
		return components.size();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(components.size());
		for(var c : this)
			out.writeObject(c);
	}

	protected Iterable<? extends Class<? extends E>> getClassRequired(Class<? extends E> cls) {
		return IteratorUtil.empty();
	}

	public static final class LockClassSet<E> implements AutoCloseable {

		private final Collection<E> addedComponent = new HashSet<>(), removedComponent = new HashSet<>();

		private final ClassSet<E> _this;
		private LockClassSet(ClassSet<E> _this) {
			this._this = _this;
		}

		private static boolean containsClass(Iterable<?> iter, Class<?> cls) {
			for(var i : iter)
				if(cls.isInstance(i))
					return true;
			return false;
		}

		public void add(E component) {
			Objects.requireNonNull(component);
			if(removedComponent.contains(component))
				removedComponent.remove(component);
			else {
				final var cls = component.getClass();
				if(addedComponent.contains(component))
					throw new IllegalArgumentException(cls + " aready added");
				addedComponent.add(component);
			}
		}

		@Override
		public void close() {
			for(E c : addedComponent) {
				final var cls = (Class<? extends E>) c.getClass();
				if(_this.contains(cls))
					throw new IllegalArgumentException("componenet "+cls.getName()+" already added to " + _this);
				final var requiredComponents = _this.getClassRequired(cls);
				for(var rc : requiredComponents)
					if(!_this.contains(rc) && !containsClass(addedComponent, rc))
						throw new IllegalArgumentException("add componet " + c + " required " + rc.getName() + " to "
								+ _this + ", also added " + addedComponent);
			}
			_this.components.addAll(addedComponent);
			for(E c : addedComponent) {
				final var cls = (Class<? extends E>) c.getClass();
				_this.addAction.event(cls, c);
			}
			for(E c : removedComponent) {
				final var cls = (Class<? extends E>) c.getClass();
				_this.removeAction.event(cls, c);
			}
			_this.components.removeAll(removedComponent);
		}

		public <T extends E> T get(Class<T> componentClass) {
			return _this.get(componentClass);
		}

		public <T extends E> T remove(Class<T> componentClass) {
			final var component = _this.get(componentClass);
			if(addedComponent.contains(component))
				addedComponent.remove(component);
			else {
				if(removedComponent.contains(component))
					throw new IllegalArgumentException(componentClass + " aready remove");
				removedComponent.add(component);
			}
			return component;
		}

	}

}
