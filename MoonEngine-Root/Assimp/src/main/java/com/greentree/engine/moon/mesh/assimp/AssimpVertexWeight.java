package com.greentree.engine.moon.mesh.assimp;

import java.io.Serializable;
import java.util.Objects;

import org.lwjgl.assimp.AIVertexWeight;

public final class AssimpVertexWeight implements Serializable {
	private static final long serialVersionUID = 1L;

	private final int mVertexId;
	private final float mWeight;

	public AssimpVertexWeight(AIVertexWeight weight) {
		mVertexId = weight.mVertexId();
		mWeight = weight.mWeight();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if((obj == null) || (getClass() != obj.getClass())) return false;
		AssimpVertexWeight other = (AssimpVertexWeight) obj;
		return mVertexId == other.mVertexId && Float.floatToIntBits(mWeight) == Float.floatToIntBits(other.mWeight);
	}

	@Override
	public int hashCode() {
		return Objects.hash(mVertexId, mWeight);
	}

	public int mVertexId() {
		return mVertexId;
	}

	public float mWeight() {
		return mWeight;
	}

	@Override
	public String toString() {
		return "AssimpVertexWeight [mVertexId=" + mVertexId + ", mWeight=" + mWeight + "]";
	}

}
