package com.greentree.engine.moon.render.pipeline.target.buffer.command;

import java.util.Objects;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.material.Material;

public record DrawMultiMesh(Iterable<StaticMesh> meshs, Material material)
		implements TargetCommand {
	
	public DrawMultiMesh {
		Objects.requireNonNull(meshs);
		Objects.requireNonNull(material);
	}
	
	public DrawMultiMesh(StaticMesh mesh, Material material) {
		this(IteratorUtil.iterable(mesh), material);
	}
	
	@Override
	public void run() {
		try(final var buffer = material.shader().buffer(material.properties())) {
			for(var mesh : meshs)
				buffer.drawMesh(mesh);
		}
	}
	
	@Override
	public TargetCommand merge(TargetCommand command) {
		if(command instanceof DrawMultiMesh c) {
			if(c.material.equals(material))
				return new DrawMultiMesh(IteratorUtil.union(meshs, c.meshs), material);
		}
		return null;
	}
	
}
