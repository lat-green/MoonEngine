package com.greentree.engine.moon.render.pipeline.target.buffer.command;

import java.util.Objects;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.render.mesh.RenderMesh;

@Deprecated
public record DrawMultiMesh(Iterable<RenderMesh> meshs) implements TargetCommand {
	
	public DrawMultiMesh {
		Objects.requireNonNull(meshs);
	}
	
	public DrawMultiMesh(RenderMesh mesh) {
		this(IteratorUtil.iterable(mesh));
	}
	
	@Override
	public void run() {
		for(var mesh : meshs) {
			mesh.bind();
			mesh.render();
			mesh.unbind();
		}
	}
	
	@Override
	public TargetCommand merge(TargetCommand command) {
		if(command instanceof DrawMultiMesh c)
			return new DrawMultiMesh(IteratorUtil.union(meshs, c.meshs));
		return null;
	}
	
	@Override
	public String toString() {
		return "DrawMultiMesh [" + IteratorUtil.size(meshs) + "]";
	}
	
	
	
}
