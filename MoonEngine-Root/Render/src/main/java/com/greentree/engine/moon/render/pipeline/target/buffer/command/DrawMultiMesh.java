package com.greentree.engine.moon.render.pipeline.target.buffer.command;

import java.util.Objects;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.pipeline.material.Shader;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;

public record DrawMultiMesh(Iterable<StaticMesh> meshs, Shader material,
		MaterialProperties properties) implements TargetCommand {
	
	public DrawMultiMesh {
		Objects.requireNonNull(meshs);
		Objects.requireNonNull(material);
	}
	
	public DrawMultiMesh(StaticMesh mesh, Shader material, MaterialProperties properties) {
		this(IteratorUtil.iterable(mesh), material, properties);
	}
	
	@Override
	public void run() {
		try(final var buffer = material.buffer(properties)) {
			for(var mesh : meshs)
				buffer.drawMesh(mesh);
		}
	}
	
	@Override
	public TargetCommand merge(TargetCommand command) {
		if(command instanceof DrawMultiMesh c) {
			if(c.material.equals(material) && c.properties.equals(properties))
				return new DrawMultiMesh(IteratorUtil.union(meshs, c.meshs), material, properties);
		}
		return null;
	}
	
}
