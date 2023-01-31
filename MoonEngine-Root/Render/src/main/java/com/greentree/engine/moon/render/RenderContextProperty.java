package com.greentree.engine.moon.render;

import com.greentree.engine.moon.module.EngineProperty;
import com.greentree.engine.moon.render.pipeline.RenderContext;

public record RenderContextProperty(RenderContext context) implements EngineProperty {
}
