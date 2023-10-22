package com.greentree.engine.moon.mesh.assimp;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import org.lwjgl.assimp.AIFace;

public final class AssimpFace implements Serializable {
	private static final long serialVersionUID = 1L;

	private final int[] mIndices;

	public AssimpFace(AIFace face) {
		final var mNumIndices = face.mNumIndices();
		final var mIndices = face.mIndices();

		this.mIndices = new int[mNumIndices];
		for(int i = 0; i < mNumIndices; i++) {
			final var index = mIndices.get();
			this.mIndices[i] = index;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if((obj == null) || (getClass() != obj.getClass())) return false;
		AssimpFace other = (AssimpFace) obj;
		return Arrays.equals(mIndices, other.mIndices);
	}

	@Override
	public int hashCode() {
		return Objects.hash(Arrays.hashCode(mIndices));
	}

	@Deprecated
	public int[] mIndices() {
		return mIndices.clone();
	}
	
	public int mIndices(int index) {
		return mIndices[index];
	}

	public int mNumIndices() {
		return mIndices.length;
	}

	@Override
	public String toString() {
		return "AssimpFace " + Arrays.toString(mIndices);
	}

}
