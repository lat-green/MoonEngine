package com.greentree.engine.moon.render.pipeline.target.buffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.render.mesh.RenderMesh;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.Shader;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.ClearRenderTargetColor;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.ClearRenderTargetDepth;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.CullFace;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.DepthTest;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.DrawMesh;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.TargetCommand;

public class PushCommandBuffer implements TargetCommandBuffer {
	
	private static final int INITIAL_CAPACITY = 10;
	private final RenderLibrary library;
	private final List<TargetCommand> commands;
	private boolean depthTest;
	private boolean cullFace;
	
	public PushCommandBuffer(RenderLibrary library) {
		this(library, INITIAL_CAPACITY);
	}
	
	public PushCommandBuffer(RenderLibrary library, int initialCapacity) {
		this.library = library;
		commands = new ArrayList<>(initialCapacity);
	}
	
	@Override
	public void clear() {
		commands.clear();
		cullFace = false;
		depthTest = false;
	}
	
	@Override
	public void clearColor(Color color) {
		push(new ClearRenderTargetColor(library, color));
	}
	
	@Override
	public void clearDepth(float depth) {
		push(new ClearRenderTargetDepth(library, depth));
	}
	
	@Override
	public void close() {
		execute();
		clear();
	}
	
	@Override
	public void disableCullFace() {
		cullFace = false;
	}
	
	@Override
	public void disableDepthTest() {
		depthTest = false;
	}
	
	@Override
	public void drawMesh(RenderMesh mesh, Shader shader, MaterialProperties properties) {
		TargetCommand command = new DrawMesh(shader, mesh, properties);
		if(cullFace)
			command = new CullFace(library, command);
		if(depthTest)
			command = new DepthTest(library, command);
		push(command);
	}
	
	@Override
	public void enableCullFace() {
		cullFace = true;
	}
	
	@Override
	public void enableDepthTest() {
		depthTest = true;
	}
	
	@Override
	public void execute() {
		for(var c : commands)
			c.run();
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
