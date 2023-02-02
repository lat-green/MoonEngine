package com.greentree.engine.moon.render.pipeline.target.buffer.command;

import java.util.Objects;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.render.mesh.RenderMesh;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.Shader;

public record DrawMultiMesh(Iterable<RenderMesh> meshs, Shader shader,
		MaterialProperties properties) implements TargetCommand {
	
	public DrawMultiMesh {
		Objects.requireNonNull(meshs);
		Objects.requireNonNull(shader);
		Objects.requireNonNull(properties);
	}
	
	public DrawMultiMesh(RenderMesh mesh, Shader shader, MaterialProperties properties) {
		this(IteratorUtil.iterable(mesh), shader, properties);
	}
	
	@Override
	public void run() {
		shader.bind();
		properties.set(shader);
		for(var mesh : meshs)
			mesh.render();
		shader.unbind();
	}
	
	@Override
	public TargetCommand merge(TargetCommand command) {
		if(command instanceof DrawMultiMesh c) {
			if(c.shader.equals(shader) && c.properties.equals(properties))
				return new DrawMultiMesh(IteratorUtil.union(meshs, c.meshs), shader, properties);
		}
		return null;
	}
	
}
