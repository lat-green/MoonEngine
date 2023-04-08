package com.greentree.engine.moon.render.window;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.engine.moon.render.pipeline.target.RenderTarget;
import com.greentree.engine.moon.render.window.callback.CharCallback;
import com.greentree.engine.moon.render.window.callback.CursorPosCallback;
import com.greentree.engine.moon.render.window.callback.DropCallback;
import com.greentree.engine.moon.render.window.callback.FramebufferSizeCallback;
import com.greentree.engine.moon.render.window.callback.KeyCallback;
import com.greentree.engine.moon.render.window.callback.MouseButtonCallback;
import com.greentree.engine.moon.render.window.callback.ScrollCallback;
import com.greentree.engine.moon.render.window.callback.WindowCloseCallback;
import com.greentree.engine.moon.render.window.callback.WindowContentScaleCallback;
import com.greentree.engine.moon.render.window.callback.WindowFocusCallback;
import com.greentree.engine.moon.render.window.callback.WindowIconifyCallback;
import com.greentree.engine.moon.render.window.callback.WindowMaximizeCallback;
import com.greentree.engine.moon.render.window.callback.WindowPosCallback;
import com.greentree.engine.moon.render.window.callback.WindowRefreshCallback;
import com.greentree.engine.moon.render.window.callback.WindowSizeCallback;

public interface Window {
	
	int getHeight();
	int getWidth();
	
	String getTitle();
	
	RenderTarget screanRenderTarget();
	
	void swapBuffer();
	boolean isShouldClose();
	void setInputMode(CursorInputMode mode);
	CursorInputMode getInputMode();
	
	
	
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
