package com.greentree.engine.moon.opengl;

import com.greentree.common.graphics.sgl.Window;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.UpdateModule;
import com.greentree.engine.moon.opengl.adapter.WindowAddapter;
import com.greentree.engine.moon.render.window.WindowProperty;

public final class OpenGLPollDelete implements LaunchModule, UpdateModule {
	
	private Window window;
	
	@Override
	public void launch(EngineProperties properties) {
		window = ((WindowAddapter) properties.get(WindowProperty.class).window()).window();
	}
	
	@Override
	public void update() {
		window.pollDelete();
	}
	
}
