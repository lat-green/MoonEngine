package com.greentree.engine.moon.render.pipeline.target.buffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.render.mesh.RenderMesh;
import com.greentree.engine.moon.render.mesh.RenderMeshUtil;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.Shader;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.ClearRenderTargetColor;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.ClearRenderTargetDepth;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.DrawMultiMesh;
import com.greentree.engine.moon.render.pipeline.target.buffer.command.TargetCommand;

public class PushCommandBuffer implements TargetCommandBuffer {
	
	private static final int INITIAL_CAPACITY = 150;
	private final RenderLibrary library;
	private final Collection<TargetCommand> commands;
	
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
		push(new DrawMultiMesh(mesh, material, properties));
	}
	
	@Override
	public void drawSkyBox(Shader material, MaterialProperties properties) {
		final var mesh = RenderMeshUtil.BOX(library);
		drawMesh(mesh, material, properties);
	}
	
	public void push(TargetCommand command) {
		Objects.requireNonNull(command);
		commands.add(command);
		merge();
	}
	
	private void merge() {
		TargetCommand a = null, b = null, merge = null;
		do {
			final var iter_a = commands.iterator();
			A :
			while(iter_a.hasNext()) {
				a = iter_a.next();
				final var iter_b = commands.iterator();
				while(iter_b.hasNext()) {
					b = iter_b.next();
					if(a == b)
						break;
					merge = a.merge(b);
					if(merge != null)
						break A;
				}
			}
			if(merge == null)
				break;
			commands.remove(a);
			commands.remove(b);
			commands.add(merge);
			merge = null;
		}while(true);
	}
	
}
