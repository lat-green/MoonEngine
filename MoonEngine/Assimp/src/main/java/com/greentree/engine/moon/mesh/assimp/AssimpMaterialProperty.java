package com.greentree.engine.moon.mesh.assimp;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import org.lwjgl.assimp.AIMaterialProperty;

public final class AssimpMaterialProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String mKey;
	private final int mSemantic;
	private final int mIndex;
	private final AssimpPropertyTypeInfo mType;
	private byte[] mData;

	public AssimpMaterialProperty(AIMaterialProperty property) {
		mKey = property.mKey().dataString();
		mSemantic = property.mSemantic();
		mIndex = property.mIndex();
		mType = AssimpPropertyTypeInfo.get(property.mType());
		final var mDataLength = property.mDataLength();
		final var mData = property.mData();
		this.mData = new byte[mDataLength];
		for(int i = 0; i < mDataLength; i++)
			this.mData[i] = mData.get();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if((obj == null) || (getClass() != obj.getClass())) return false;
		AssimpMaterialProperty other = (AssimpMaterialProperty) obj;
		return Arrays.equals(mData, other.mData) && mIndex == other.mIndex && Objects.equals(mKey, other.mKey) && mSemantic == other.mSemantic && mType == other.mType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(mData);
		result = prime * result + Objects.hash(mIndex, mKey, mSemantic, mType);
		return result;
	}

	@Deprecated
	public byte[] mData() {
		return mData.clone();
	}
	
	public byte mData(int index) {
		return mData[index];
	}

	public int mDataLength() {
		return mData.length;
	}
	public int mIndex() {
		return mIndex;
	}
	public String mKey() {
		return mKey;
	}
	public int mSemantic() {
		return mSemantic;
	}
	public AssimpPropertyTypeInfo mType() {
		return mType;
	}
	@Override
	public String toString() {
		return "AssimpMaterialProperty [mKey=" + mKey + ", mSemantic=" + mSemantic + ", mIndex=" + mIndex + ", mType=" + mType + ", mData=" + Arrays.toString(mData) + "]";
	}

}
