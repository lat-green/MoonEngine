package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.component.Component;


public class TwoRequiredFilter extends AbstractFilter {

	private final Class<? extends Component> required1, required2;
	
	public TwoRequiredFilter(World world, Class<? extends Component> required1, Class<? extends Component> required2) {
		super(world);
		this.required1 = required1;
		this.required2 = required2;
		
		for(var e : world)
			if(e.contains(required1) && e.contains(required2))
				addFilteredEntity(e);

		lc(world.onAddComponent(required1, e -> {
			if(e.contains(required2))
				addFilteredEntity(e);
		}));
		lc(world.onRemoveComponent(required1, e -> removeFilteredEntity(e)));
		lc(world.onAddComponent(required2, e -> {
			if(e.contains(required1))
				addFilteredEntity(e);
		}));
		lc(world.onRemoveComponent(required2, e -> removeFilteredEntity(e)));
	}

	public Class<? extends Component> getRequired1() {
		return required1;
	}
	public Class<? extends Component> getRequired2() {
		return required2;
	}

}