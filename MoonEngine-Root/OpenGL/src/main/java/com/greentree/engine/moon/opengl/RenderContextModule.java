package com.greentree.engine.moon.opengl;

import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.annotation.CreateProperty;
import com.greentree.engine.moon.module.annotation.ReadProperty;
import com.greentree.engine.moon.render.RenderContextProperty;


public class RenderContextModule implements LaunchModule {
	
	@ReadProperty({WindowProperty.class})
	@CreateProperty({RenderContextProperty.class})
	@Override
	public void launch(EngineProperties context) {
		final var window = context.get(WindowProperty.class).window();
		context.add(new RenderContextProperty(new GLRenderContext(window)));
	}
	
}
