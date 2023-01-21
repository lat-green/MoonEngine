package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.component.Component;

public final class OnlyRequiredFilter extends AbstractFilter {

	private final Iterable<? extends Class<? extends Component>> required;

	public OnlyRequiredFilter(World world, Iterable<? extends Class<? extends Component>> required) {
		super(world);
		this.required = required;
		
		for(var e : world)
			tryAddFilteredEntity(e);

		for(var r : required) {
			lc(world.onAddComponent(r, e -> tryAddFilteredEntity(e)));
    		lc(world.onRemoveComponent(r, e -> removeFilteredEntity(e)));
    	}
	}

	private void tryAddFilteredEntity(Entity e) {
		for(var r : required)
			if(!e.contains(r))
				return;
		addFilteredEntity(e);
	}

	public Iterable<? extends Class<? extends Component>> getRequired() {
		return required;
	}

}