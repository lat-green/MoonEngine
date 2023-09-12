package com.greentree.engine.moon.opengl.adapter;

import com.greentree.common.graphics.sgl.Window;
import com.greentree.engine.moon.render.window.WindowLibrary;

public record GLFWWindowLibrary(GLRenderLibrary render) implements WindowLibrary {

    @Override
    public WindowAdapter createWindow(String title, int width, int height) {
        return new WindowAdapter(new Window(title, width, height), render);
    }

}
