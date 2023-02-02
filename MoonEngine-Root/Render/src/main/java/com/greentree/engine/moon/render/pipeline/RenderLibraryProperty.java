package com.greentree.engine.moon.render.pipeline;

import com.greentree.engine.moon.ecs.WorldComponent;
import com.greentree.engine.moon.module.EngineProperty;

public record RenderLibraryProperty(RenderLibrary library)
		implements EngineProperty, WorldComponent {
}
