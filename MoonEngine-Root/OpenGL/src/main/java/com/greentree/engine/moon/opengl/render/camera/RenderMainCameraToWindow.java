package com.greentree.engine.moon.opengl.render.camera;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.enums.gl.GLClientState;
import com.greentree.common.graphics.sgl.enums.gl.GLPrimitive;
import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2D;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.common.graphics.sgl.window.Window;
import com.greentree.engine.moon.base.scene.EnginePropertiesWorldComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.ReadComponent;
import com.greentree.engine.moon.ecs.annotation.ReadWorldComponent;
import com.greentree.engine.moon.ecs.annotation.WriteWorldComponent;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.opengl.WindowProperty;
import com.greentree.engine.moon.render.camera.Cameras;


public class RenderMainCameraToWindow implements InitSystem, UpdateSystem, DestroySystem {
	
	private Window window;
	private Cameras cameras;
	
	public RenderMainCameraToWindow() {
	}
	
	private static void renderTexture(GLTexture2D texture) {
		texture.bind();
		glEnableClientState(GLClientState.VERTEX_ARRAY.glEnum);
		glEnableClientState(GLClientState.TEXTURE_COORD_ARRAY.glEnum);
		try(var stack = MemoryStack.create(GLType.FLOAT.size * 2 * 8).push()) {
			glVertexPointer(2, GLType.FLOAT.glEnum, 0, stack.floats(-1f, -1f, -1f, 1f, 1f, 1f, 1f, -1f));
			glTexCoordPointer(2, GLType.FLOAT.glEnum, 0, stack.floats(0, 0, 0, 1, 1, 1, 1, 0));
		}
		glDrawArrays(GLPrimitive.QUADS.glEnum, 0, 4);
		glDisableClientState(GLClientState.TEXTURE_COORD_ARRAY.glEnum);
		glDisableClientState(GLClientState.VERTEX_ARRAY.glEnum);
		GLTexture2DImpl.BINDER.unbind();
	}
	
	@Override
	public void destroy() {
		cameras = null;
		window = null;
	}
	
	@ReadWorldComponent({EnginePropertiesWorldComponent.class,Cameras.class})
	@Override
	public void init(World world) {
		cameras = world.get(Cameras.class);
		window = world.get(EnginePropertiesWorldComponent.class).properties().get(WindowProperty.class).window();
	}
	
	@WriteWorldComponent({EnginePropertiesWorldComponent.class})
	@ReadComponent({CameraTarget.class})
	@Override
	public void update() {
		final var camera = cameras.main();
		final var buffer = (GLTexture2D) camera.get(CameraTarget.class).buffer().getColorTexture(0);
		
		glViewport(0, 0, window.getWidth(), window.getHeight());
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_ACCUM_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		renderTexture(buffer);
		window.swapBuffer();
	}
	
}
