package com.greentree.engine.moon.opengl.base;

import java.util.ArrayList;
import java.util.Collection;

import com.greentree.common.renderer.material.Material;
import com.greentree.common.renderer.material.TextureProperty;
import com.greentree.common.renderer.mesh.GraphicsMesh;
import com.greentree.common.renderer.opengl.command.ClearRenderTargetColor;
import com.greentree.common.renderer.opengl.command.ClearRenderTargetDepth;
import com.greentree.common.renderer.opengl.command.DrawMesh;
import com.greentree.common.renderer.opengl.command.DrawSkyBox;
import com.greentree.common.renderer.opengl.command.DrawTexture;
import com.greentree.common.renderer.opengl.command.OpenGLCommand;
import com.greentree.common.renderer.opengl.command.OpenGLContext;
import com.greentree.common.renderer.pipeline.buffer.CommandBuffer;
import com.greentree.commons.image.Color;


public final class OpenGLCommandBuffer implements CommandBuffer {
	
	private final Collection<OpenGLCommand> commands = new ArrayList<>();
	private final OpenGLContext context;
	
	public OpenGLCommandBuffer(OpenGLContext context) {
		super();
		this.context = context;
	}
	
	@Override
	public void clearRenderTargetColor(Color color) {
		commands.add(new ClearRenderTargetColor(color));
	}
	
	@Override
	public void clearRenderTargetDepth(float depth) {
		commands.add(new ClearRenderTargetDepth(depth));
	}
	
	@Override
	public void close() {
		for(var c : commands)
			c.run(context);
		commands.clear();
	}
	
	@Override
	public void drawMesh(GraphicsMesh mesh, Material material) {
		commands.add(new DrawMesh(mesh, material));
	}
	
	@Override
	public void drawSkyBox(Material material) {
		commands.add(new DrawSkyBox(material));
	}
	
	@Override
	public void drawTexture(TextureProperty texture) {
		commands.add(new DrawTexture(texture));
	}
	
}
