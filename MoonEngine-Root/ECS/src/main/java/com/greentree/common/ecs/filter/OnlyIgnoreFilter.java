package com.greentree.common.ecs.filter;

import com.greentree.common.ecs.Entity;
import com.greentree.common.ecs.World;
import com.greentree.common.ecs.component.Component;

public final class OnlyIgnoreFilter extends AbstractFilter {

	private final Iterable<? extends Class<? extends Component>> ignore;

	public OnlyIgnoreFilter(World world, Iterable<? extends Class<? extends Component>> ignore) {
		super(world);
		this.ignore = ignore;
		
		for(var e : world)
			tryAddFilteredEntity(e);

		for(var i : ignore) {
			lc(world.onRemoveComponent(i, e -> tryAddFilteredEntity(e)));
    		lc(world.onAddComponent(i, e -> removeFilteredEntity(e)));
    	}
	}

	private void tryAddFilteredEntity(Entity e) {
		for(var r : ignore)
			if(e.contains(r))
				return;
		addFilteredEntity(e);
	}

	public Iterable<? extends Class<? extends Component>> getIgnore() {
		return ignore;
	}

}