package com.greentree.engine.moon.mesh.assimp.assets.mesh;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;
import org.lwjgl.system.MemoryUtil;

import com.greentree.commons.data.resource.Resource;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.mesh.assimp.AssimpScene;

public class AssimpSceneAssetSerializator implements AssetSerializator<AssimpScene> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(Resource.class, key);
	}
	
	@Override
	public Value<AssimpScene> load(LoadContext manager, AssetKey ckey) {
		if(manager.canLoad(Resource.class, ckey)) {
			final var resource = manager.load(Resource.class, ckey);
			return manager.map(resource, new AssimpSceneAsssetFunction());
		}
		return null;
	}
	
	public static final class AssimpSceneAsssetFunction implements Value1Function<Resource, AssimpScene> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public AssimpScene apply(Resource res) {
			byte[] _data;
			ByteBuffer data;
			
			try(final var in = res.open();) {
				_data = in.readAllBytes();
				data = MemoryUtil.memCalloc(_data.length);
				data.put(_data);
				data.flip();
			}catch(IOException e) {
				throw new IllegalArgumentException(e);
			}
			try {
				try(AIScene scene = Assimp.aiImportFileFromMemory(data, Assimp.aiProcess_Triangulate
						| Assimp.aiProcess_ValidateDataStructure | Assimp.aiProcess_OptimizeMeshes, "");) {
					if(scene == null) {
						final var error = Assimp.aiGetErrorString();
						throw new IllegalArgumentException(error);
					}
					final var s = new AssimpScene(scene, AssimpScene.NOT_LOAD_MATERIAL | AssimpScene.NOT_LOAD_TEXTURES);
					return s;
				}
			}finally {
				MemoryUtil.memFree(data);
			}
		}
	}
	
}
