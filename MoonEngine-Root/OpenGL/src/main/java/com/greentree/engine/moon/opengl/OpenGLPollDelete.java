package com.greentree.engine.moon.opengl;

import com.greentree.common.graphics.sgl.Window;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.UpdateModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.opengl.adapter.WindowAdapter;
import com.greentree.engine.moon.render.window.WindowProperty;

public final class OpenGLPollDelete implements LaunchModule, UpdateModule {

    private Window window;

    @ReadProperty(WindowProperty.class)
    @Override
    public void launch(EngineProperties properties) {
        window = ((WindowAdapter) properties.get(WindowProperty.class).window()).window();
    }

    @Override
    public void update() {
        window.pollDelete();
    }

}
