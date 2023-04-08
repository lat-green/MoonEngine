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
import com.greentree.engine.moon.render.mesh.MeshUtil;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;
import com.greentree.engine.moon.render.pipeline.material.LazyProperty;
import com.greentree.engine.moon.render.pipeline.material.MaterialPropertiesBase;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;
import com.greentree.engine.moon.render.window.Window;
import com.greentree.engine.moon.render.window.WindowProperty;

public final class RenderMainCamera implements InitSystem, UpdateSystem, DestroySystem {
	
	private Window window;
	private TargetCommandBuffer buffer;
	
	
	@Override
	public void destroy() {
		window = null;
		buffer.clear();
		buffer.close();
		buffer = null;
	}
	
	@ReadWorldComponent({WindowProperty.class, Cameras.class})
	@ReadComponent({CameraTarget.class})
	@Override
	public void init(World world) {
		window = world.get(WindowProperty.class).window();
		var cameras = world.get(Cameras.class);
		final var rmesh = MeshUtil.QUAD;
		final var shader = MaterialUtil.getDefaultTextureShader();
		final var properties = new MaterialPropertiesBase();
		properties.put("render_texture",
				new LazyProperty(() -> cameras.main().get(CameraTarget.class).target().getColorTexture()));
		buffer = window.screanRenderTarget().buffer();
		buffer.drawMesh(rmesh, shader, properties);
	}
	
	@WriteWorldComponent({RenderLibraryProperty.class,WindowProperty.class})
	@ReadWorldComponent({Cameras.class})
	@ReadComponent({CameraTarget.class})
	@Override
	public void update() {
		window.swapBuffer();
		buffer.execute();
	}
	
}
