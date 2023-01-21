package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.component.Component;


public class TwoRequiredOneIgnoreFilter extends AbstractFilter {

	private final Class<? extends Component> required1, required2, ignore;


	public TwoRequiredOneIgnoreFilter(World world, Class<? extends Component> required1, Class<? extends Component> required2, Class<? extends Component> ignore) {
		super(world);
		this.required1 = required1;
		this.required2 = required2;
		this.ignore = ignore;

		for(var e : world)
			if(e.contains(required1) && e.contains(required2) && !e.contains(ignore))
				addFilteredEntity(e);

		lc(world.onAddComponent(required1, e -> {
			if(e.contains(required2) && !e.contains(ignore))
				addFilteredEntity(e);
		}));
		lc(world.onRemoveComponent(required1, this::removeFilteredEntity));
		lc(world.onAddComponent(required2, e -> {
			if(e.contains(required1) && !e.contains(ignore))
				addFilteredEntity(e);
		}));
		lc(world.onRemoveComponent(required2, this::removeFilteredEntity));
		lc(world.onRemoveComponent(ignore, e -> {
			if(e.contains(required1) && e.contains(required2))
				addFilteredEntity(e);
		}));
		lc(world.onAddComponent(ignore, this::removeFilteredEntity));
	}

	public Class<? extends Component> getIgnore() {
		return ignore;
	}

	public Class<? extends Component> getRequired1() {
		return required1;
	}
	public Class<? extends Component> getRequired2() {
		return required2;
	}

}