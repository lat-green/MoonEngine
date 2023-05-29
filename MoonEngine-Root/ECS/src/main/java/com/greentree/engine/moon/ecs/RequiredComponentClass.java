package com.greentree.engine.moon.ecs;

import java.util.Map;

import com.greentree.engine.moon.ecs.component.Component;

public record RequiredComponentClass(Class<? extends Component>[] value) implements RequiredComponent {
	
	@SuppressWarnings("unchecked")
	public RequiredComponentClass(Map<String, Object> values) {
		this((Class<? extends Component>[]) values.get("value"));
	}
	
	@Override
	public Class<? extends RequiredComponent> annotationType() {
		return RequiredComponent.class;
	}
	
}
