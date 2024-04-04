package com.greentree.engine.moon.opengl;

import com.greentree.common.graphics.sglfw.SimpleGLFW;
import com.greentree.engine.moon.modules.UpdateModule;

public class WindowUpdateEvents implements UpdateModule {

    @Override
    public void update() {
        SimpleGLFW.INSTANCE.glfwPollEvents();
    }

}
