package com.greentree.engine.moon.mesh.assimp.assets.mesh;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;

import com.greentree.commons.data.resource.FileResource;
import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.util.InputStreamUtil;
import com.greentree.commons.util.exception.WrappedException;
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
	
	public static final class AssimpSceneAsssetFunction
			implements Value1Function<Resource, AssimpScene> {
		
		private static final long serialVersionUID = 1L;
		
		private static File getFile(Resource res) {
			if(res instanceof FileResource fileRes) {
				final var file = fileRes.getFile();
				return file;
			}
			try {
				final var temp = Files.createTempFile("", res.getName()).toAbsolutePath().toFile();
				try(final var out = new FileOutputStream(temp)) {
					try(final var in = res.open()) {
						InputStreamUtil.copy(in, out);
					}
				}
				return temp;
			}catch(IOException e) {
				throw new WrappedException(e);
			}
		}
		
		@Override
		public AssimpScene apply(Resource res) {
			final var file = getFile(res);
			try(AIScene scene = Assimp.aiImportFile(file.toString(),
					Assimp.aiProcess_Triangulate | Assimp.aiProcess_ValidateDataStructure);) {
				if(scene == null) {
					final var error = Assimp.aiGetErrorString();
					throw new IllegalArgumentException(error);
				}
				
				final var s = new AssimpScene(scene,
						AssimpScene.NOT_LOAD_MATERIAL | AssimpScene.NOT_LOAD_TEXTURES);
				return s;
			}
		}
	}
	
}
