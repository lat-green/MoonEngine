package com.greentree.engine.moon.base.property.world;

import java.util.Map;

import com.greentree.engine.moon.base.component.UseStage;
import com.greentree.engine.moon.ecs.WorldComponent;

public record UseWorldComponentClass(Class<? extends WorldComponent>[] value, UseStage state)
		implements UseWorldComponent {
	
	
	@SuppressWarnings("unchecked")
	public UseWorldComponentClass(Map<String, Object> values) {
		this((Class<? extends WorldComponent>[]) values.get("value"), (UseStage) values.get("state"));
	}
	
	@Override
	public Class<? extends UseWorldComponent> annotationType() {
		return UseWorldComponent.class;
	}
	
}
