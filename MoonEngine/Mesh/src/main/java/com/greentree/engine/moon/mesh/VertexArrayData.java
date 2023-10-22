package com.greentree.engine.moon.mesh;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import com.greentree.commons.util.iterator.IteratorUtil;

public record VertexArrayData(int[] indecies, Iterable<AttributeData> vbos)
		implements Serializable {
	
	public VertexArrayData {
		Objects.requireNonNull(indecies);
		Objects.requireNonNull(vbos);
		for(var vbo : vbos)
			Objects.requireNonNull(vbo);
	}
	
	@Override
	public String toString() {
		return "VertexArrayData [" + Arrays.toString(indecies) + ", " + IteratorUtil.toString(vbos)
				+ "]";
	}
	
	
	
}
