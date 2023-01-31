package com.greentree.engine.moon.mesh;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public record AttributeData(float[] vertex, int... sizes) implements Serializable {
	
	public AttributeData {
		Objects.requireNonNull(vertex);
		Objects.requireNonNull(sizes);
	}
	
	@Override
	public String toString() {
		return "AttributeData [" + Arrays.toString(vertex) + ", " + Arrays.toString(sizes) + "]";
	}
	
}
