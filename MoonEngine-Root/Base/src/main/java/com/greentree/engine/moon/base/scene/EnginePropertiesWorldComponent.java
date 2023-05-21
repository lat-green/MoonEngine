package com.greentree.engine.moon.base.scene;

import com.greentree.engine.moon.ecs.WorldComponent;
import com.greentree.engine.moon.modules.property.EngineProperties;

public record EnginePropertiesWorldComponent(EngineProperties properties) implements WorldComponent {
	
}
