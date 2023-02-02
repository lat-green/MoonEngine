package com.greentree.engine.moon.render;

import com.greentree.engine.moon.ecs.WorldComponent;
import com.greentree.engine.moon.module.EngineProperty;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;

public record RenderContextProperty(RenderLibrary context)
		implements EngineProperty, WorldComponent {
}
