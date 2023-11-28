package com.greentree.engine.moon.mesh.assimp.assets.mesh;

import com.greentree.commons.data.resource.Resource;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.mesh.assimp.AssimpScene;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.nio.ByteBuffer;

public class AssimpSceneAssetSerializator implements AssetSerializator<AssimpScene> {

    @Override
    public Asset<AssimpScene> load(AssetLoader.Context manager, AssetKey ckey) {
        final var resource = manager.load(Resource.class, ckey);
        return AssetKt.map(resource, new AssimpSceneAsssetFunction());
    }

    public static final class AssimpSceneAsssetFunction implements Value1Function<Resource, AssimpScene> {

        private static final long serialVersionUID = 1L;

        @Override
        public AssimpScene apply(Resource res) {
            byte[] _data;
            ByteBuffer data;
            try (final var in = res.open()) {
                _data = in.readAllBytes();
                data = MemoryUtil.memCalloc(_data.length);
                data.put(_data);
                data.flip();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
            try {
                try (AIScene scene = Assimp.aiImportFileFromMemory(data, Assimp.aiProcess_Triangulate
                        | Assimp.aiProcess_ValidateDataStructure | Assimp.aiProcess_OptimizeMeshes, "")) {
                    if (scene == null) {
                        final var error = Assimp.aiGetErrorString();
                        throw new IllegalArgumentException(error);
                    }
                    final var s = new AssimpScene(scene, AssimpScene.NOT_LOAD_MATERIAL | AssimpScene.NOT_LOAD_TEXTURES);
                    return s;
                }
            } finally {
                MemoryUtil.memFree(data);
            }
        }

    }

}
