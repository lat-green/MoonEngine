package com.greentree.engine.moon.render.assets.mesh;

import com.greentree.common.renderer.mesh.GraphicsMesh;
import com.greentree.common.renderer.mesh.MeshFace;
import com.greentree.common.renderer.mesh.VertexIndex;
import com.greentree.common.renderer.mesh.assimp.AssimpScene;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;

public class AssimpMeshAssetSerializator implements AssetSerializator<GraphicsMesh> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(AssimpScene.class, key);
	}
	
	@Override
	public Value<GraphicsMesh> load(LoadContext manager, AssetKey ckey) {
		{
			final var scene = manager.load(AssimpScene.class, ckey);
			if(scene != null)
				return manager.map(scene, new AssimpAsssetFunction());
		}
		return null;
	}
	
	public static final class AssimpAsssetFunction
			implements Value1Function<AssimpScene, GraphicsMesh> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public GraphicsMesh apply(AssimpScene scene) {
			final var mesh = scene.mMeshes(0);
			
			final var builder = GraphicsMesh.builder();
			
			for(int i = 0; i < mesh.mNumVertices(); i++) {
				final var position = mesh.mVertices(i);
				final var normal = mesh.mNormals(i);
				final var texCoord = mesh.mTextureCoords(0, i);
				
				builder.addVertices(position);
				builder.addNormals(normal);
				builder.addTextureCoordinates(texCoord);
			}
			
			for(int f = 0; f < mesh.mNumFaces(); f++) {
				final var face = mesh.mFaces(f);
				for(int ind = 0; ind < face.mNumIndices(); ind += 3) {
					final var av = face.mIndices(ind + 0);
					final var bv = face.mIndices(ind + 1);
					final var cv = face.mIndices(ind + 2);
					
					final var a = new VertexIndex(av, av, av);
					final var b = new VertexIndex(bv, bv, bv);
					final var c = new VertexIndex(cv, cv, cv);
					
					final var fB = MeshFace.builder();
					fB.addVertex(a);
					fB.addVertex(b);
					fB.addVertex(c);
					builder.addFaces(fB.build());
				}
			}
			
			return builder.build();
		}
		
	}
	
}
