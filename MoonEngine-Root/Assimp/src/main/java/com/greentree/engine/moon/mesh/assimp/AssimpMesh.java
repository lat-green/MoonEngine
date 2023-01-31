package com.greentree.engine.moon.mesh.assimp;

import java.io.Serializable;

import org.lwjgl.assimp.AIBone;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.Assimp;

import com.greentree.commons.math.vector.FinalVector2f;
import com.greentree.commons.math.vector.FinalVector3f;

public final class AssimpMesh implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String mName;
	private final int mMaterialIndex;
	private final AssimpFace[] mFaces;
	private final FinalVector3f[] mVertices;
	private FinalVector3f[] mNormals;
	private FinalVector2f[][] mTextureCoords = new FinalVector2f[Assimp.AI_MAX_NUMBER_OF_TEXTURECOORDS][];

	private AssimpBone[] mBones;

	public AssimpMesh(AIMesh mesh) {
		mName = mesh.mName().dataString();
		mMaterialIndex = mesh.mMaterialIndex();

		final var mFaces = mesh.mFaces();
		final var mNumFaces = mesh.mNumFaces();

		this.mFaces = new AssimpFace[mNumFaces];
		for(int i = 0; i < mNumFaces; i++) {
			final var f = mFaces.get();
			final var face = new AssimpFace(f);
			this.mFaces[i] = face;
		}

		final var mNumVertices = mesh.mNumVertices();

		final var mVertices = mesh.mVertices();
		this.mVertices = new FinalVector3f[mNumVertices];
		for(int i = 0; i < mNumVertices; i++) {
			final var av = mVertices.get();
			final var v = new FinalVector3f(av.x(), av.y(), av.z());
			this.mVertices[i] = v;
		}

		final var mNormals = mesh.mNormals();
		this.mNormals = new FinalVector3f[mNumVertices];
		for(int i = 0; i < mNumVertices; i++) {
			final var av = mNormals.get();
			final var v = new FinalVector3f(av.x(), av.y(), av.z());
			this.mNormals[i] = v;
		}

		for(int i = 0; i < Assimp.AI_MAX_NUMBER_OF_TEXTURECOORDS; i++) {
			final var mTextureCoords = mesh.mTextureCoords(i);
			if(mTextureCoords == null)
				continue;
			final var textureCoords = new FinalVector2f[mNumVertices];
			for(int t = 0; t < mNumVertices; t++) {
				final var av = mTextureCoords.get();
				textureCoords[t] = new FinalVector2f(av.x(), av.y());
			}
			this.mTextureCoords[i] = textureCoords;
		}

		final var mNumBones = mesh.mNumBones();
		final var mBones = mesh.mBones();
		this.mBones = new AssimpBone[mNumBones];
		for(int i = 0; i < mNumBones; i++) {
			final var abone = AIBone.create(mBones.get());
			final var bone = new AssimpBone(abone);
			this.mBones[i] = bone;
		}
	}
	@Deprecated
	public AssimpFace[] mFaces() {
		return mFaces.clone();
	}
	public AssimpFace mFaces(int index) {
		return mFaces[index];
	}
	public int mMaterialIndex() {
		return mMaterialIndex;
	}
	public String mName() {
		return mName;
	}
	@Deprecated
	public FinalVector3f[] mNormals() {
		return mNormals.clone();
	}
	public FinalVector3f mNormals(int index) {
		return mNormals[index];
	}
	public int mNumFaces() {
		return mFaces.length;
	}
	public int mNumVertices() {
		return mVertices.length;
	}
	@Deprecated
	public FinalVector2f[] mTextureCoords(int index) {
		final var arr = mTextureCoords[index];
		if(arr == null)
			return null;
		return arr.clone();
	}
	public int mNumTextureCoords(int index) {
		final var arr = mTextureCoords[index];
		if(arr == null)
			return -1;
		return arr.length;
	}
	public FinalVector2f mTextureCoords(int index, int tindex) {
		final var arr = mTextureCoords[index];
		if(arr == null)
			return null;
		return arr[tindex];
	}
	@Deprecated
	public FinalVector3f[] mVertices() {
		return mVertices.clone();
	}
	public FinalVector3f mVertices(int index) {
		return mVertices[index];
	}

}
