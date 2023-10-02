package com.greentree.engine.moon.opengl;

import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.opengl.adapter.GLRenderContext;
import com.greentree.engine.moon.render.pipeline.RenderContextProperty;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;

public final class CreateRenderContextModule implements LaunchModule {

    @ReadProperty(RenderLibraryProperty.class)
    @CreateProperty({RenderContextProperty.class})
    @Override
    public void launch(EngineProperties context) {
        var library = context.get(RenderLibraryProperty.class).library();
        context.add(new RenderContextProperty(new GLRenderContext(library)));
    }

}
