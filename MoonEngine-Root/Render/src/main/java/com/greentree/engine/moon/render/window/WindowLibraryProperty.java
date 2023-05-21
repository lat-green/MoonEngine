package com.greentree.engine.moon.render.window;

import com.greentree.engine.moon.ecs.WorldComponent;
import com.greentree.engine.moon.modules.property.EngineProperty;

public record WindowLibraryProperty(WindowLibrary library)
implements EngineProperty, WorldComponent {
	
}
