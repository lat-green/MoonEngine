package com.greentree.common.ecs.filter;

import com.greentree.common.ecs.Entity;
import com.greentree.common.ecs.World;
import com.greentree.common.ecs.component.Component;

public final class MultiRequiredMultiIgnoreFilter extends AbstractFilter {

	private final Iterable<? extends Class<? extends Component>> required;
	private final Iterable<? extends Class<? extends Component>> ignore;

	public MultiRequiredMultiIgnoreFilter(World world, Iterable<? extends Class<? extends Component>> required, Iterable<? extends Class<? extends Component>> ignore) {
		super(world);
		this.required = required;
		this.ignore = ignore;
		
		for(var e : world) {
			tryAddFilteredEntity(e);
		}
			
		for(var r : required) {
			lc(world.onAddComponent(r, e -> tryAddFilteredEntity(e)));
    		lc(world.onRemoveComponent(r, e -> removeFilteredEntity(e)));
    	}
		for(var i : ignore) {
			lc(world.onAddComponent(i, e -> removeFilteredEntity(e)));
    		lc(world.onRemoveComponent(i, e -> tryAddFilteredEntity(e)));
    	}
	}

	private void tryAddFilteredEntity(Entity e) {
		for(var r : required)
			if(!e.contains(r))
				return;
		for(var i : ignore)
			if(e.contains(i))
				return;
		addFilteredEntity(e);
	}

	public Iterable<? extends Class<? extends Component>> getRequired() {
		return required;
	}

}