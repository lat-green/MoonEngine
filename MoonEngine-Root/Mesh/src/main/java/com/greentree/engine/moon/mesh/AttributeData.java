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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(sizes);
		result = prime * result + Arrays.hashCode(vertex);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof AttributeData)) {
			return false;
		}
		AttributeData other = (AttributeData) obj;
		return Arrays.equals(sizes, other.sizes) && Arrays.equals(vertex, other.vertex);
	}
	
	@Override
	public String toString() {
		return "AttributeData [" + Arrays.toString(vertex) + ", " + Arrays.toString(sizes) + "]";
	}
	
}
