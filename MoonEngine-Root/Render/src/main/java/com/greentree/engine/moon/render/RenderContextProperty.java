package com.greentree.engine.moon.render;

import com.greentree.engine.moon.ecs.WorldComponent;
import com.greentree.engine.moon.module.EngineProperty;
import com.greentree.engine.moon.render.pipeline.RenderLibraryContext;

public record RenderContextProperty(RenderLibraryContext context)
		implements EngineProperty, WorldComponent {
}
