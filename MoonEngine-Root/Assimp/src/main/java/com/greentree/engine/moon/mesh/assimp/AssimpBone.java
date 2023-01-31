package com.greentree.engine.moon.mesh.assimp;

import java.io.Serializable;
import java.util.Arrays;

import org.joml.Matrix4f;
import org.lwjgl.assimp.AIBone;
import org.lwjgl.assimp.AIMatrix4x4;

public final class AssimpBone implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String mName;
	private final Matrix4f mOffsetMatrix;
	private final AssimpVertexWeight[] mWeights;

	public AssimpBone(AIBone bone) {
		mName = bone.mName().dataString();
		mOffsetMatrix = mat(bone.mOffsetMatrix());

		final var mNumWeights = bone.mNumWeights();
		final var mWeights = bone.mWeights();
		this.mWeights = new AssimpVertexWeight[mNumWeights];
		for(int i = 0; i < mNumWeights; i++) {
			final var aw = mWeights.get();
			final var w = new AssimpVertexWeight(aw);
			this.mWeights[i] = w;
		}
	}

	public String mName() {
		return mName;
	}

	public Matrix4f mOffsetMatrix() {
		return mOffsetMatrix;
	}

	@Override
	public String toString() {
		return "AssimpBone [mName=" + mName + ", mOffsetMatrix=" + mOffsetMatrix + ", mWeights=" + Arrays.toString(mWeights) + "]";
	}

	@Deprecated
	public AssimpVertexWeight[] mWeights() {
		return mWeights.clone();
	}
	
	public int mNumWeights() {
		return mWeights.length;
	}
	
	public AssimpVertexWeight mWeights(int index) {
		return mWeights[index];
	}

	private Matrix4f mat(AIMatrix4x4 m) {
		return new Matrix4f(m.a1(), m.a2(), m.a3(), m.a4(),
				m.b1(), m.b2(), m.b3(), m.b4(),
				m.c1(), m.c2(), m.c3(), m.c4(),
				m.d1(), m.d2(), m.d3(), m.d4());
	}

}
