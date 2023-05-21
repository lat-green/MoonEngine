package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.component.Component;

public interface WorldFilterBuilder extends AbstractFilterBuilder {
	
	@Override
	WorldFilterBuilder ignore(Class<? extends Component> cls);
	
	@Override
	WorldFilterBuilder required(Class<? extends Component> cls);
	
	Filter build();
	
}
