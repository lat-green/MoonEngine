package com.greentree.engine.moon.ecs.component;


public class DefaultComponentPool<C extends Component> implements AbstractComponentPool<C> {
	
	@Override
	public C newComponent(ConstructorParameters parameters) {
		return null;
	}
	
}
