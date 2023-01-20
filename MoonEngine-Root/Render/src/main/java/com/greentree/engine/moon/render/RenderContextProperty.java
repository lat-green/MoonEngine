package com.greentree.engine.moon.render;

import com.greentree.common.renderer.pipeline.RenderContext;
import com.greentree.engine.moon.module.EngineProperty;

public record RenderContextProperty(RenderContext context) implements EngineProperty {
}
