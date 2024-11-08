package com.greentree.engine.moon.opengl;

import com.greentree.common.graphics.sglfw.SimpleGLFW;
import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.base.property.modules.DestroyProperty;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.opengl.adapter.GLFWWindowLibrary;
import com.greentree.engine.moon.opengl.adapter.GLRenderContext;
import com.greentree.engine.moon.render.pipeline.RenderContextProperty;
import com.greentree.engine.moon.render.window.WindowLibraryProperty;
import kotlin.Unit;

public final class CreateWindowLibraryModule implements LaunchModule, TerminateModule {

    @ReadProperty({RenderContextProperty.class})
    @CreateProperty({WindowLibraryProperty.class})
    @Override
    public void launch(EngineProperties context) {
        SimpleGLFW.INSTANCE.glfwMainThread(() -> {
            return Unit.INSTANCE;
        });
        var render = (GLRenderContext) context.get(RenderContextProperty.class).value();
        context.add(new WindowLibraryProperty(new GLFWWindowLibrary(render)));
    }

    @DestroyProperty({WindowLibraryProperty.class})
    @Override
    public void terminate() {
    }

}
