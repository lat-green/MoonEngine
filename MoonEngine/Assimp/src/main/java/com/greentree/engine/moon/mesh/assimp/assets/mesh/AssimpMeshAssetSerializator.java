package com.greentree.engine.moon.mesh.assimp.assets.mesh;

import com.greentree.engine.moon.assets.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.provider.AssetProvider;
import com.greentree.engine.moon.assets.provider.AssetProviderKt;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader;
import com.greentree.engine.moon.mesh.GraphicsMesh;
import com.greentree.engine.moon.mesh.StaticMesh.ValueVertexIndex;
import com.greentree.engine.moon.mesh.assimp.AssimpScene;

public class AssimpMeshAssetSerializator implements AssetSerializator<GraphicsMesh> {

    @Override
    public AssetProvider<GraphicsMesh> load(AssetLoader.Context manager, AssetKey key) {
        final var scene = manager.load(AssimpScene.class, key);
        return AssetProviderKt.map(scene, new AssimpAsssetFunction());
    }

    public static final class AssimpAsssetFunction
            implements Value1Function<AssimpScene, GraphicsMesh> {

        private static final long serialVersionUID = 1L;

        @Override
        public GraphicsMesh apply(AssimpScene scene) {
            final var mesh = scene.mMeshes(0);
            final var builder = GraphicsMesh.builder();
            for (int f = 0; f < mesh.mNumFaces(); f++) {
                final var face = mesh.mFaces(f);
                for (int ind = 0; ind < face.mNumIndices(); ind += 3) {
                    final var ai = face.mIndices(ind);
                    final var bi = face.mIndices(ind + 1);
                    final var ci = face.mIndices(ind + 2);
                    final var av = mesh.mVertices(ai);
                    final var bv = mesh.mVertices(bi);
                    final var cv = mesh.mVertices(ci);
                    final var an = mesh.mNormals(ai);
                    final var bn = mesh.mNormals(bi);
                    final var cn = mesh.mNormals(ci);
                    final var at = mesh.mTextureCoords(0, ai);
                    final var bt = mesh.mTextureCoords(0, bi);
                    final var ct = mesh.mTextureCoords(0, ci);
                    final var a = new ValueVertexIndex(av, an, at);
                    final var b = new ValueVertexIndex(bv, bn, bt);
                    final var c = new ValueVertexIndex(cv, cn, ct);
                    builder.addFaces(a, b, c);
                }
            }
            return builder.build();
        }

    }

}
