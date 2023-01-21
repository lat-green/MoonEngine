package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.component.Component;


public class OneIgnoreFilter extends AbstractFilter {

	private final Class<? extends Component> ignore;

	public OneIgnoreFilter(World world, Class<? extends Component> ignore) {
		super(world);
		this.ignore = ignore;
		
		for(var e : world)
			if(!e.contains(ignore))
				addFilteredEntity(e);

		lc(world.onAddComponent(ignore, e -> removeFilteredEntity(e)));
		lc(world.onRemoveComponent(ignore, e -> addFilteredEntity(e)));
	}

	public Class<? extends Component> getIgnore() {
		return ignore;
	}

}