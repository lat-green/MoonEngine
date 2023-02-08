package com.greentree.engine.moon.render.pipeline.target.buffer.command;

import java.util.Objects;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.render.mesh.RenderMesh;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.Shader;

public record BindShader(Shader shader, RenderMesh mesh, Iterable<MaterialProperties> properties)
		implements TargetCommand {
	
	public BindShader {
		Objects.requireNonNull(shader);
		Objects.requireNonNull(properties);
		Objects.requireNonNull(mesh);
	}
	
	public BindShader(Shader shader, RenderMesh mesh, MaterialProperties properties) {
		this(shader, mesh, IteratorUtil.iterable(properties));
	}
	
	@Override
	public void run() {
		shader.bind();
		mesh.bind();
		final var iter = properties.iterator();
		MaterialProperties last = null;
		if(iter.hasNext()) {
			final var p = iter.next();
			p.set(shader);
			mesh.render();
			last = p;
		}
		while(iter.hasNext()) {
			final var p = iter.next();
			final var diff = p.get(last);
			diff.set(shader);
			mesh.render();
			last = p;
		}
		mesh.unbind();
		shader.unbind();
	}
	
	@Override
	public String toString() {
		return "BindShader [" + shader + ", " + IteratorUtil.size(properties) + ", " + mesh + "]";
	}
	
	@Override
	public TargetCommand merge(TargetCommand command) {
		if(command == this)
			return this;
		if(command instanceof BindShader c && c.shader.equals(shader) && c.mesh.equals(mesh)) {
			return new BindShader(shader, mesh, IteratorUtil.union(properties, c.properties));
		}
		return null;
	}
	
}
