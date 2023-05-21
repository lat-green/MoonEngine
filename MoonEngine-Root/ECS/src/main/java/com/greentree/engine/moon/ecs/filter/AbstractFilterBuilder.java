package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.component.Component;

public interface AbstractFilterBuilder {

	
	AbstractFilterBuilder ignore(Class<? extends Component> cls);
	
	AbstractFilterBuilder required(Class<? extends Component> cls);
}
