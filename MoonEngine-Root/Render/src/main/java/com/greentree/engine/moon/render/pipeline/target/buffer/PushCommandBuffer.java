package com.greentree.engine.moon.render.pipeline.target.buffer;

import java.util.ArrayList;
import java.util.Collection;

import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.material.Material;
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
		commands.add(command);
	}
	
	@Override
	public void close() {
		for(var c : commands)
			c.run();
		commands.clear();
	}
	
	@Override
	public void drawMesh(StaticMesh mesh, Material material) {
		push(new DrawMultiMesh(mesh, material));
	}
	
}
