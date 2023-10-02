package com.greentree.engine.moon.render.pipeline;

import com.greentree.commons.graphics.smart.pipeline.RenderPipeline;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class RenderPipelineExecuteSystem implements InitSystem, UpdateSystem {

    private RenderPipeline pipeline;

    @ReadProperty(RenderPipelineProperty.class)
    @Override
    public void init(SceneProperties properties) {
        pipeline = properties.get(RenderPipelineProperty.class).value();
    }

    @Override
    public void update() {
        pipeline.execute();
    }

}
