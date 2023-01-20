package com.greentree.engine.moon.base.scene;

import com.greentree.common.ecs.WorldComponent;
import com.greentree.engine.moon.module.EngineProperties;

public record EnginePropertiesWorldComponent(EngineProperties properties) implements WorldComponent {
	
}
