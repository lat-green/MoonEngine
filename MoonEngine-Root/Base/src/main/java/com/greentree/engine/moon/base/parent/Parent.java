package com.greentree.engine.moon.base.parent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.component.Component;

public final class Parent implements Component {
	
	private static final long serialVersionUID = 1L;
	
	private Entity parent;
	private final Collection<Entity> childrens = new ArrayList<>();
	
	public boolean isEmpty() {
		return parent == null && childrens.isEmpty();
	}
	
	public static void setParent(Entity entity, Entity parent) {
		final var entity_p = getParent(entity);
		if(entity_p.parent == parent)
			return;
		if(parent == null) {
			final var old_parent_p = getParent(entity_p.parent);
			old_parent_p.childrens.remove(entity);
			entity_p.parent = null;
		}else {
			if(entity_p.parent == null) {
				final var parent_p = getParent(parent);
				parent_p.childrens.add(entity);
				entity_p.parent = parent;
			}else {
				final var old_parent_p = getParent(entity_p.parent);
				old_parent_p.childrens.remove(entity);
				
				final var parent_p = getParent(parent);
				parent_p.childrens.add(entity);
				entity_p.parent = parent;
			}
		}
	}
	
	private static Parent getParent(Entity entity) {
		Objects.requireNonNull(entity);
		if(entity.contains(Parent.class))
			return entity.get(Parent.class);
		final var p = new Parent();
		entity.add(p);
		return p;
	}
	
	public Entity getParent() {
		return parent;
	}
	
	public Iterable<? extends Entity> getChildrens() {
		return childrens;
	}
	
	@Override
	public Component copy() {
		final var copy = new Parent();
		copy.parent = parent;
		copy.childrens.addAll(childrens);
		return copy;
	}
	
	
	
	@Override
	public String toString() {
		return "Parent [" + parent + "]";
	}
	
}
