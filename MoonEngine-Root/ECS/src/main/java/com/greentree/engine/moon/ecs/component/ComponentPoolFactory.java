package com.greentree.engine.moon.ecs.component;

public interface ComponentPoolFactory {
	
	<C extends Component> AbstractComponentPool<C> newComponentPool(Class<C> componentClass);
	
}
