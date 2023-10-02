package com.greentree.engine.moon.render.pipeline;

import com.greentree.commons.graphics.smart.pipeline.ForwardRenderPipeline;
import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.render.scene.RenderSceneProperty;

public class ForwardRenderPipelineConfigSystem implements InitSystem {

    @ReadProperty({RenderSceneProperty.class, RenderContextProperty.class})
    @CreateProperty(RenderPipelineProperty.class)
    @Override
    public void init(SceneProperties properties) {
        var scene = properties.get(RenderSceneProperty.class).value();
        var context = properties.get(RenderContextProperty.class).value();
        var pipeline = new ForwardRenderPipeline(scene, context);
        properties.add(new RenderPipelineProperty(pipeline));
    }

}
