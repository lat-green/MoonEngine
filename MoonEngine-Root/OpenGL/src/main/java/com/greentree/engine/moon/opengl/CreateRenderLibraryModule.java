package com.greentree.engine.moon.opengl;

import com.greentree.engine.moon.base.modules.CreateProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.opengl.adapter.GLRenderLibrary;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;


public final class CreateRenderLibraryModule implements LaunchModule {
	
	@CreateProperty({RenderLibraryProperty.class})
	@Override
	public void launch(EngineProperties context) {
		context.add(new RenderLibraryProperty(new GLRenderLibrary()));
	}
	
}
