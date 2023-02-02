package com.greentree.engine.moon.render.pipeline.target.buffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.pipeline.material.Shader;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.DrawMultiMesh;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.TargetCommand;

public abstract class PushCommandBuffer implements TargetCommandBuffer {
	
	private final Collection<TargetCommand> commands;
	
	public PushCommandBuffer() {
		commands = new ArrayList<>();
	}
	
	public PushCommandBuffer(int initialCapacity) {
		commands = new ArrayList<>(initialCapacity);
	}
	
	public void push(TargetCommand command) {
		Objects.requireNonNull(command);
		commands.add(command);
	}
	
	@Override
	public void close() {
		for(var c : commands)
			c.run();
		commands.clear();
	}
	
	@Override
	public void drawMesh(StaticMesh mesh, Shader material, MaterialProperties properties) {
		push(new DrawMultiMesh(mesh, material, properties));
	}
	
}
