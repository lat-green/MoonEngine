package com.greentree.engine.moon.render;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.ReadComponent;
import com.greentree.engine.moon.ecs.annotation.ReadWorldComponent;
import com.greentree.engine.moon.ecs.annotation.WriteWorldComponent;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.render.camera.CameraTarget;
import com.greentree.engine.moon.render.camera.Cameras;
import com.greentree.engine.moon.render.mesh.RenderMeshUtil;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;
import com.greentree.engine.moon.render.pipeline.material.MaterialPropertiesBase;
import com.greentree.engine.moon.render.window.Window;
import com.greentree.engine.moon.render.window.WindowProperty;

public final class RenderMainCamera implements InitSystem, UpdateSystem, DestroySystem {
	
	private RenderLibrary library;
	private Window window;
	private Cameras cameras;
	
	
	@Override
	public void destroy() {
		library = null;
		window = null;
		cameras = null;
	}
	
	@ReadWorldComponent({RenderLibraryProperty.class,WindowProperty.class,Cameras.class})
	@Override
	public void init(World world) {
		library = world.get(RenderLibraryProperty.class).library();
		window = world.get(WindowProperty.class).window();
		cameras = world.get(Cameras.class);
	}
	
	@WriteWorldComponent({RenderLibraryProperty.class,WindowProperty.class})
	@ReadWorldComponent({Cameras.class})
	@ReadComponent({CameraTarget.class})
	@Override
	public void update() {
		final var rmesh = RenderMeshUtil.QUAD(library);
		final var shader = MaterialUtil.getDefaultTextureShader(library);
		final var properties = new MaterialPropertiesBase();
		final var camera = cameras.main();
		properties.put("render_texture", camera.get(CameraTarget.class).target().getColorTexture());
		window.swapBuffer();
		try(final var buffer = library.screanRenderTarget().buffer()) {
			buffer.drawMesh(rmesh, shader, properties);
		}
	}
	
}
