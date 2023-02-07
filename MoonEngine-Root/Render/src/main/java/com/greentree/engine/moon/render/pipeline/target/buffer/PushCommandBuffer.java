package com.greentree.engine.moon.render.pipeline.target.buffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.render.mesh.RenderMesh;
import com.greentree.engine.moon.render.mesh.RenderMeshUtil;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.Shader;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.BindShader;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.ClearRenderTargetColor;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.ClearRenderTargetDepth;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.DrawMultiMesh;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.TargetCommand;

public class PushCommandBuffer implements TargetCommandBuffer {
	
	private static final int INITIAL_CAPACITY = 150;
	private final RenderLibrary library;
	private final List<TargetCommand> commands;
	
	public PushCommandBuffer(RenderLibrary library) {
		this(library, INITIAL_CAPACITY);
	}
	
	public PushCommandBuffer(RenderLibrary library, int initialCapacity) {
		this.library = library;
		commands = new ArrayList<>(initialCapacity);
	}
	
	@Override
	public void clearRenderTargetColor(Color color) {
		push(new ClearRenderTargetColor(library, color));
	}
	
	@Override
	public void clearRenderTargetDepth(float depth) {
		push(new ClearRenderTargetDepth(library, depth));
	}
	
	@Override
	public void close() {
		for(var c : commands)
			c.run();
		commands.clear();
	}
	
	@Override
	public void drawMesh(RenderMesh mesh, Shader material, MaterialProperties properties) {
		push(new BindShader(material, properties, new DrawMultiMesh(mesh)));
	}
	
	@Override
	public void drawSkyBox(Shader material, MaterialProperties properties) {
		final var mesh = RenderMeshUtil.BOX(library);
		drawMesh(mesh, material, properties);
	}
	
	public void push(TargetCommand command) {
		Objects.requireNonNull(command);
		while(commands.size() > 1) {
			final var c = commands.get(commands.size() - 1);
			final var m = c.merge(command);
			if(m == null)
				break;
			command = m;
			commands.remove(commands.size() - 1);
		}
		commands.add(command);
	}
	
}
