package com.greentree.engine.moon.render.window;

import com.greentree.engine.moon.ecs.WorldComponent;
import com.greentree.engine.moon.module.EngineProperty;

public record WindowLibraryProperty(WindowLibrary library)
		implements EngineProperty, WorldComponent {
	
}
