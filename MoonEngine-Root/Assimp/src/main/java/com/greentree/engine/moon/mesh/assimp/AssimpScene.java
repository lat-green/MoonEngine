package com.greentree.engine.moon.mesh.assimp;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AITexture;

import com.greentree.commons.image.image.ImageData;
import com.greentree.commons.image.loader.ImageDataLoader;
import com.greentree.commons.image.loader.ImageIOImageData;

public class AssimpScene implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int NOT_LOAD_TEXTURES = 1, NOT_LOAD_MATERIAL = 2;
	
	private static final int ALL_FLAGS = NOT_LOAD_MATERIAL | NOT_LOAD_TEXTURES;
	private static final int NEG_ALL_FLAGS = ~ALL_FLAGS;
	
	private final AssimpMesh[] mMeshes;
	
	private final AssimpMaterial[] mMaterials;
	private final ImageData[] mTextures;
	
	public AssimpScene(AIScene scene) {
		this(scene, 0);
	}
	
	public AssimpScene(AIScene scene, int flags) {
		if((flags & NEG_ALL_FLAGS) != 0)
			throw new IllegalArgumentException();
		final var mNumMeshes = scene.mNumMeshes();
		final var mMeshes = scene.mMeshes();
		this.mMeshes = new AssimpMesh[mNumMeshes];
		for(int i = 0; i < mNumMeshes; i++) {
			final var amesh = AIMesh.create(mMeshes.get());
			final var mesh = new AssimpMesh(amesh);
			this.mMeshes[i] = mesh;
		}
		
		if((flags & NOT_LOAD_MATERIAL) == 0) {
			final var mNumMaterials = scene.mNumMaterials();
			final var mMaterials = scene.mMaterials();
			this.mMaterials = new AssimpMaterial[mNumMaterials];
			for(int i = 0; i < mNumMeshes; i++) {
				final var am = AIMaterial.create(mMaterials.get());
				final var m = new AssimpMaterial(am);
				this.mMaterials[i] = m;
			}
		}else
			mMaterials = new AssimpMaterial[0];
		
		if((flags & NOT_LOAD_MATERIAL) == 0) {
			final var mNumTextures = scene.mNumTextures();
			final var mTextures = scene.mTextures();
			this.mTextures = new ImageData[mNumTextures];
			for(int i = 0; i < mNumTextures; i++) {
				final var at = AITexture.create(mTextures.get());
				final var t = loadImage(at);
				this.mTextures[i] = t;
			}
		}else
			mTextures = new ImageData[0];
	}
	
	
	private static ImageDataLoader getLoader(String format) {
		return ImageIOImageData.IMAGE_DATA_LOADER;
		//		return switch (format) {
		//			case "png" -> PNGImageData.IMAGE_DATA_LOADER;
		//			default ->
		//				throw new IllegalArgumentException("Unexpected value: " + format);
		//		};
	}
	
	@Deprecated
	public AssimpMaterial[] mMaterials() {
		return mMaterials.clone();
	}
	
	public AssimpMaterial mMaterials(int index) {
		return mMaterials[index];
	}
	
	@Deprecated
	public AssimpMesh[] mMeshes() {
		return mMeshes.clone();
	}
	
	public AssimpMesh mMeshes(int index) {
		return mMeshes[index];
	}
	
	public int mNumMaterials() {
		return mMaterials.length;
	}
	
	public int mNumMeshes() {
		return mMeshes.length;
	}
	
	public int mNumTextures() {
		return mTextures.length;
	}
	
	@Deprecated
	public ImageData[] mTextures() {
		return mTextures.clone();
	}
	
	public ImageData mTextures(int index) {
		return mTextures[index];
	}
	
	private ImageData loadImage(AITexture texture) {
		final var mWidth = texture.mWidth();
		final var mHeight = texture.mHeight();
		final var achFormatHintString = texture.achFormatHintString();
		
		//		if(mHeight == 0) {
		//			final var address = texture.pcData().address0();
		//			final var buffer = MemoryUtil.memByteBuffer(address, texture.mWidth());
		//			
		//			final var loader = getLoader(achFormatHintString);
		//			try(final var in = new BufferInputStream(buffer);) {
		//				return loader.loadImage(in);
		//			}catch(IOException e) {
		//				throw new WrappedException(e);
		//			}
		//		}else {
		//			final var data = texture.pcData(mWidth * mHeight);
		//			
		//			return null;
		//		}
		return null;
	}
	
	private static final class BufferInputStream extends InputStream {
		
		private Iterator<Byte> buffer;
		
		public BufferInputStream(ByteBuffer buffer) {
			final var buf = new ArrayList<Byte>();
			while(buffer.hasRemaining())
				buf.add(buffer.get());
			this.buffer = buf.iterator();
		}
		
		@Override
		public int read() throws IOException {
			if(buffer.hasNext()) {
				final var b = buffer.next();
				return Byte.toUnsignedInt(b);
			}
			return -1;
		}
		
	}
	
}
