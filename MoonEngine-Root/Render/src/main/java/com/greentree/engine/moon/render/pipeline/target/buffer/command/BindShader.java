package com.greentree.engine.moon.render.pipeline.target.buffer.command;

import java.util.Objects;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.Shader;

public record BindShader(Shader shader, Iterable<MaterialProperties> properties, TargetCommand base)
		implements TargetCommand {
	
	public BindShader {
		Objects.requireNonNull(shader);
		Objects.requireNonNull(properties);
		Objects.requireNonNull(base);
	}
	
	public BindShader(Shader shader, MaterialProperties properties, TargetCommand base) {
		this(shader, IteratorUtil.iterable(properties), base);
	}
	
	@Override
	public void run() {
		shader.bind();
		final var iter = properties.iterator();
		MaterialProperties last = null;
		if(iter.hasNext()) {
			final var p = iter.next();
			p.set(shader);
			base.run();
			last = p;
		}
		while(iter.hasNext()) {
			final var p = iter.next();
			final var diff = p.get(last);
			diff.set(shader);
			base.run();
			last = p;
		}
		shader.unbind();
	}
	
	@Override
	public TargetCommand merge(TargetCommand command) {
		if(command == this)
			return this;
		if(command instanceof BindShader c && c.shader.equals(shader)) {
			
			final var m = base.merge(c.base);
			if(m != null)
				return new BindShader(shader, IteratorUtil.union(properties, c.properties), m);
		}
		return null;
	}
	
}
