package com.greentree.engine.moon.base.component;

import java.util.Map;

import com.greentree.engine.moon.ecs.component.Component;

public record UseComponentClass(Class<? extends Component>[] value, UseStage state) implements UseComponent {
	
	
	@SuppressWarnings("unchecked")
	public UseComponentClass(Map<String, Object> values) {
		this((Class<? extends Component>[]) values.get("value"), (UseStage) values.get("state"));
	}
	
	@Override
	public Class<? extends UseComponent> annotationType() {
		return UseComponent.class;
	}
	
}
