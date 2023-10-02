package com.greentree.engine.moon.render.pipeline;

import com.greentree.commons.graphics.smart.pipeline.RenderPipeline;
import com.greentree.engine.moon.ecs.scene.SceneProperty;

public record RenderPipelineProperty(RenderPipeline value) implements SceneProperty {

}
