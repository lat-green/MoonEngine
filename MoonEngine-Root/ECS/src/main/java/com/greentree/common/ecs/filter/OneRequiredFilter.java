package com.greentree.common.ecs.filter;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.component.Component;

public final class OneRequiredFilter extends AbstractFilter {

	private final Class<? extends Component> required;

	public OneRequiredFilter(World world, Class<? extends Component> required) {
		super(world);
		this.required = required;
		
		for(var e : world)
			if(e.contains(required))
				addFilteredEntity(e);

		lc(world.onAddComponent(required, e -> addFilteredEntity(e)));
		lc(world.onRemoveComponent(required, e -> removeFilteredEntity(e)));
	}

	public Class<? extends Component> getRequired() {
		return required;
	}

}