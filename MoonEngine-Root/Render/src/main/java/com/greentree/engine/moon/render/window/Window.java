package com.greentree.engine.moon.render.window;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.graphics.smart.target.RenderTarget;
import com.greentree.engine.moon.render.window.callback.*;

public interface Window {

    int getHeight();

    int getWidth();

    String getTitle();

    RenderTarget screanRenderTarget();

    void swapBuffer();

    boolean isShouldClose();

    CursorInputMode getInputMode();

    void setInputMode(CursorInputMode mode);

    ListenerCloser addCharCallback(CharCallback callback);

    ListenerCloser addCloseCallback(WindowCloseCallback callback);

    ListenerCloser addContentScaleCallback(WindowContentScaleCallback callback);

    ListenerCloser addCursorPosCallback(CursorPosCallback callback);

    ListenerCloser addDropCallback(DropCallback callback);

    ListenerCloser addFocusCallback(WindowFocusCallback callback);

    ListenerCloser addFramebufferSizeCallback(FramebufferSizeCallback callback);

    ListenerCloser addIconifyCallback(WindowIconifyCallback callback);

    ListenerCloser addKeyCallback(KeyCallback callback);

    ListenerCloser addMaximizeCallback(WindowMaximizeCallback callback);

    ListenerCloser addMouseButtonCallback(MouseButtonCallback callback);

    ListenerCloser addPosCallback(WindowPosCallback callback);

    ListenerCloser addRefreshCallback(WindowRefreshCallback callback);

    ListenerCloser addScrollCallback(ScrollCallback callback);

    ListenerCloser addSizeCallback(WindowSizeCallback callback);

    void shouldClose();

    @Deprecated
    void makeCurrent();

}
