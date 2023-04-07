package com.greentree.engine.moon.opengl.adapter.buffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.AttributeData;
import com.greentree.engine.moon.opengl.adapter.GLRenderLibrary;
import com.greentree.engine.moon.opengl.adapter.buffer.command.ClearRenderTargetColor;
import com.greentree.engine.moon.opengl.adapter.buffer.command.ClearRenderTargetDepth;
import com.greentree.engine.moon.opengl.adapter.buffer.command.CullFace;
import com.greentree.engine.moon.opengl.adapter.buffer.command.DepthTest;
import com.greentree.engine.moon.opengl.adapter.buffer.command.DrawMesh;
import com.greentree.engine.moon.opengl.adapter.buffer.command.TargetCommand;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;
import com.greentree.engine.moon.render.shader.ShaderProgramData;

public class PushCommandBuffer implements TargetCommandBuffer {
	
	private static final int INITIAL_CAPACITY = 10;
	private final GLRenderLibrary library;
	private final List<TargetCommand> commands;
	private boolean depthTest;
	private boolean cullFace;
	
	public PushCommandBuffer(GLRenderLibrary library) {
		this(library, INITIAL_CAPACITY);
	}
	
	public PushCommandBuffer(GLRenderLibrary library, int initialCapacity) {
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
		push(new ClearRenderTargetColor(color));
	}
	
	@Override
	public void clearDepth(float depth) {
		push(new ClearRenderTargetDepth(depth));
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
	public void drawMesh(AttributeData mesh, ShaderProgramData shader, MaterialProperties properties) {
		var rmesh = library.build(mesh);
		var rshader = library.build(shader);
		
		TargetCommand command = new DrawMesh(rshader, rmesh, properties);
		if(cullFace)
			command = new CullFace(command);
		if(depthTest)
			command = new DepthTest(command);
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
