package com.greentree.engine.moon.mesh.assimp;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMaterialProperty;


public final class AssimpMaterial implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final int mNumAllocated;
	private final AssimpMaterialProperty[] mProperties;

	public AssimpMaterial(AIMaterial material) {
		mNumAllocated = material.mNumAllocated();
		
		final var mNumProperties = material.mNumProperties();
		final var mProperties = material.mProperties();
		this.mProperties = new AssimpMaterialProperty[mNumProperties];
		for(int i = 0; i < mNumProperties; i++) {
			final var amp = AIMaterialProperty.create(mProperties.get());
			final var mp = new AssimpMaterialProperty(amp);
			this.mProperties[i] = mp;
		}
	}

	public int mNumAllocated() {
		return mNumAllocated;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(mProperties);
		result = prime * result + Objects.hash(mNumAllocated);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		AssimpMaterial other = (AssimpMaterial) obj;
		return mNumAllocated == other.mNumAllocated && Arrays.equals(mProperties, other.mProperties);
	}

	@Override
	public String toString() {
		return "AssimpMaterial [mNumAllocated=" + mNumAllocated + ", mProperties=" + Arrays.toString(mProperties) + "]";
	}

	@Deprecated
	public AssimpMaterialProperty[] mProperties() {
		return mProperties.clone();
	}
	public AssimpMaterialProperty mProperties(int index) {
		return mProperties[index];
	}
	public int mNumProperties() {
		return mProperties.length;
	}
	
}
