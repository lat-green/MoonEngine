package com.greentree.engine.moon.opengl;

import com.greentree.common.graphics.sgl.SGLFW;
import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.opengl.adapter.GLFWWindowLibrary;
import com.greentree.engine.moon.opengl.adapter.GLRenderLibrary;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;
import com.greentree.engine.moon.render.window.WindowLibraryProperty;


public final class CreateWindowLibraryModule implements LaunchModule, TerminateModule {
	
	@ReadProperty({RenderLibraryProperty.class})
	@CreateProperty({WindowLibraryProperty.class})
	@Override
	public void launch(EngineProperties context) {
		SGLFW.init();
		
		var render = (GLRenderLibrary) context.get(RenderLibraryProperty.class).library();
		context.add(new WindowLibraryProperty(new GLFWWindowLibrary(render)));
	}
	
	@Override
	public void terminate() {
		SGLFW.terminate();
	}
	
}
