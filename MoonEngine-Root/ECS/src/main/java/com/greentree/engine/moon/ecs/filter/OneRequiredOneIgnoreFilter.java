package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.component.Component;

public final class OneRequiredOneIgnoreFilter extends AbstractFilter {

	private final Class<? extends Component> required;
	private final Class<? extends Component> ignore;

	public OneRequiredOneIgnoreFilter(World world, Class<? extends Component> required, Class<? extends Component> ignore) {
		super(world);
		this.required = required;
		this.ignore = ignore;
		
		for(var e : world)
			if(e.contains(required) && !e.contains(ignore))
				addFilteredEntity(e);

		lc(world.onAddComponent(required, e -> {
			if(!e.contains(ignore))
				addFilteredEntity(e);
		}));
		lc(world.onRemoveComponent(required, e -> removeFilteredEntity(e)));
		lc(world.onRemoveComponent(ignore, e -> {
			if(e.contains(required))
				addFilteredEntity(e);
		}));
		lc(world.onAddComponent(ignore, e -> removeFilteredEntity(e)));
	}

	public Class<? extends Component> getRequired() {
		return required;
	}

	public Class<? extends Component> getIgnore() {
		return ignore;
	}

}