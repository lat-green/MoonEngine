package com.greentree.engine.moon.opengl;

import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.annotation.CreateProperty;
import com.greentree.engine.moon.opengl.adapter.GLFWWindowLibrary;
import com.greentree.engine.moon.render.window.WindowLibraryProperty;


public final class CreateWindowLibraryModule implements LaunchModule {
	
	@CreateProperty({WindowLibraryProperty.class})
	@Override
	public void launch(EngineProperties context) {
		context.add(new WindowLibraryProperty(new GLFWWindowLibrary()));
	}
	
}
