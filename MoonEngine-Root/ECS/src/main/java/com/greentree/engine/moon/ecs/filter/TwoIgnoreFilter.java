package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.component.Component;


public class TwoIgnoreFilter extends AbstractFilter {

	private final Class<? extends Component> ignore1, ignore2;

	public TwoIgnoreFilter(World world, Class<? extends Component> ignore1, Class<? extends Component> ignore2) {
		super(world);
		this.ignore1 = ignore1;
		this.ignore2 = ignore2;
		
		for(var e : world)
			if(!e.contains(ignore1) && !e.contains(ignore2))
				addFilteredEntity(e);

		lc(world.onRemoveComponent(ignore1, e -> {
			if(!e.contains(ignore2))
				addFilteredEntity(e);
		}));
		lc(world.onAddComponent(ignore1, e -> removeFilteredEntity(e)));
		lc(world.onRemoveComponent(ignore2, e -> {
			if(!e.contains(ignore1))
				addFilteredEntity(e);
		}));
		lc(world.onAddComponent(ignore2, e -> removeFilteredEntity(e)));
	}

	public Class<? extends Component> getIgnore1() {
		return ignore1;
	}
	public Class<? extends Component> getIgnore2() {
		return ignore2;
	}

}