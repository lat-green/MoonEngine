package com.greentree.engine.moon.opengl.adapter;

import com.greentree.common.graphics.sgl.Window;
import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.graphics.smart.target.RenderTarget;
import com.greentree.engine.moon.opengl.GLEnums;
import com.greentree.engine.moon.render.window.CursorInputMode;
import com.greentree.engine.moon.render.window.callback.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public record WindowAdapter(Window window, RenderTarget target)
        implements com.greentree.engine.moon.render.window.Window {

    @Override
    public int getHeight() {
        return window.getHeight();
    }

    @Override
    public int getWidth() {
        return window.getWidth();
    }

    @Override
    public String getTitle() {
        return window.getTitle();
    }

    @Override
    public RenderTarget screanRenderTarget() {
        return target;
    }

    @Override
    public void swapBuffer() {
        window.swapBuffer();
        glViewport(0, 0, window.getWidth(), window.getHeight());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_ACCUM_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    @Override
    public boolean isShouldClose() {
        return window.isShouldClose();
    }

    @Override
    public CursorInputMode getInputMode() {
        final var glfw = glfwGetInputMode(window.glID, GLFW_CURSOR);
        return switch (glfw) {
            case GLFW_CURSOR_DISABLED -> CursorInputMode.DISABLED;
            case GLFW_CURSOR_HIDDEN -> CursorInputMode.HIDDEN;
            case GLFW_CURSOR_NORMAL -> CursorInputMode.NORMAL;
            default -> throw new IllegalArgumentException("Unexpected value: " + glfw);
        };
    }

    @Override
    public void setInputMode(CursorInputMode mode) {
        final var glfw = switch (mode) {
            case DISABLED -> GLFW_CURSOR_DISABLED;
            case HIDDEN -> GLFW_CURSOR_HIDDEN;
            case NORMAL -> GLFW_CURSOR_NORMAL;
        };
        glfwSetInputMode(window.glID, GLFW_CURSOR, glfw);
    }

    @Override
    public ListenerCloser addCharCallback(CharCallback callback) {
        return window.addCharCallback(chars -> callback.invoke(chars));
    }

    @Override
    public ListenerCloser addCloseCallback(WindowCloseCallback callback) {
        return window.addCloseCallback(() -> callback.invoke());
    }

    @Override
    public ListenerCloser addContentScaleCallback(WindowContentScaleCallback callback) {
        return window.addContentScaleCallback((xscale, yscale) -> callback.invoke(xscale, yscale));
    }

    @Override
    public ListenerCloser addCursorPosCallback(CursorPosCallback callback) {
        return window.addCursorPosCallback((x, y) -> callback.invoke(x, y));
    }

    @Override
    public ListenerCloser addDropCallback(DropCallback callback) {
        return window.addDropCallback(names -> callback.invoke(names));
    }

    @Override
    public ListenerCloser addFocusCallback(WindowFocusCallback callback) {
        return window.addFocusCallback(focused -> callback.invoke(focused));
    }

    @Override
    public ListenerCloser addFramebufferSizeCallback(FramebufferSizeCallback callback) {
        return window.addFramebufferSizeCallback((width, height) -> callback.invoke(width, height));
    }

    @Override
    public ListenerCloser addIconifyCallback(WindowIconifyCallback callback) {
        return window.addIconifyCallback(iconified -> callback.invoke(iconified));
    }

    @Override
    public ListenerCloser addKeyCallback(KeyCallback callback) {
        return window.addKeyCallback((key, scancode, action, mods) -> callback.invoke(GLEnums.get(key), scancode,
                GLEnums.get(action), GLEnums.get(mods)));
    }

    @Override
    public ListenerCloser addMaximizeCallback(WindowMaximizeCallback callback) {
        return window.addMaximizeCallback(maximized -> callback.invoke(maximized));
    }

    @Override
    public ListenerCloser addMouseButtonCallback(MouseButtonCallback callback) {
        return window.addMouseButtonCallback((button, action, mods) -> callback.invoke(GLEnums.get(button), GLEnums.get(action), GLEnums.get(mods)));
    }

    @Override
    public ListenerCloser addPosCallback(WindowPosCallback callback) {
        return window.addPosCallback((xpos, ypos) -> callback.invoke(xpos, ypos));
    }

    @Override
    public ListenerCloser addRefreshCallback(WindowRefreshCallback callback) {
        return window.addRefreshCallback(() -> callback.invoke());
    }

    @Override
    public ListenerCloser addScrollCallback(ScrollCallback callback) {
        return window.addScrollCallback((xoffset, yoffset) -> callback.invoke(xoffset, yoffset));
    }

    @Override
    public ListenerCloser addSizeCallback(WindowSizeCallback callback) {
        return window.addSizeCallback((width, height) -> callback.invoke(width, height));
    }

    @Override
    public void shouldClose() {
        window.shouldClose();
    }

    @Override
    public void makeCurrent() {
        window.makeCurrent();
    }

}
