package com.greentree.engine.moon.render.buffer;

import java.util.Objects;

public record VertexArrayData<EBO, VBO>(EBO ebo, Iterable<VBO> vbos) {
	
	public VertexArrayData {
		Objects.requireNonNull(vbos);
	}
	
}
