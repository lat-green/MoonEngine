package com.greentree.engine.moon.opengl.base;

import java.util.ArrayList;
import java.util.Collection;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.opengl.command.ClearRenderTargetColor;
import com.greentree.engine.moon.opengl.command.ClearRenderTargetDepth;
import com.greentree.engine.moon.opengl.command.DrawMesh;
import com.greentree.engine.moon.opengl.command.DrawSkyBox;
import com.greentree.engine.moon.opengl.command.OpenGLCommand;
import com.greentree.engine.moon.opengl.command.OpenGLContext;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.Shader;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;


public final class OpenGLCommandBuffer implements TargetCommandBuffer {
	
	private final Collection<OpenGLCommand> commands = new ArrayList<>();
	private final OpenGLContext context;
	
	public OpenGLCommandBuffer(OpenGLContext context) {
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
		for(var c : commands) {
			System.out.println(c);
			c.run(context);
		}
		commands.clear();
	}
	
	@Override
	public void drawMesh(StaticMesh mesh, Shader material, MaterialProperties properties) {
		commands.add(new DrawMesh(mesh, material, properties));
		
	}
	
	@Override
	public void drawSkyBox(Shader material, MaterialProperties properties) {
		commands.add(new DrawSkyBox(material, properties));
		
	}
	
}
