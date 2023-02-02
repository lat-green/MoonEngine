package com.greentree.engine.moon.opengl;

import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.annotation.CreateProperty;
import com.greentree.engine.moon.opengl.adapter.GLRenderLibrary;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;


public class CreateRenderLibraryModule implements LaunchModule {
	
	@CreateProperty({RenderLibraryProperty.class})
	@Override
	public void launch(EngineProperties context) {
		context.add(new RenderLibraryProperty(new GLRenderLibrary()));
	}
	
}
