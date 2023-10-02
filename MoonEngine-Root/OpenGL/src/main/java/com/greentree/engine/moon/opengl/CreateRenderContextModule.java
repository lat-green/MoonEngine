package com.greentree.engine.moon.opengl;

import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.opengl.adapter.GLRenderContext;
import com.greentree.engine.moon.render.pipeline.RenderContextProperty;

public class CreateRenderContextModule implements LaunchModule {

    @CreateProperty(RenderContextProperty.class)
    @Override
    public void launch(EngineProperties properties) {
        properties.add(new RenderContextProperty(new GLRenderContext()));
    }

}
