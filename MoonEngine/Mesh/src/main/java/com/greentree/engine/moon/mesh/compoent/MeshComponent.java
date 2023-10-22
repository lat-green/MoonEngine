package com.greentree.engine.moon.mesh.compoent;

import com.greentree.engine.moon.mesh.Mesh;
import com.greentree.engine.moon.mesh.StaticMesh;

public interface MeshComponent<MESH extends Mesh> {
	
	VertexStaticMeshVertexComponent VERTEX = VertexStaticMeshVertexComponent.INSTANCE;
	NormalStaticMeshVertexComponent NORMAL = NormalStaticMeshVertexComponent.INSTANCE;
	TextuteCoordinatStaticMeshVertexComponent TEXTURE_COORDINAT = TextuteCoordinatStaticMeshVertexComponent.INSTANCE;
	TangentStaticMeshFaceComponent TANGENT = TangentStaticMeshFaceComponent.INSTANCE;
	
	int size();
	
	static int[] sizes(MeshComponent<?>... components) {
		final var ss = new int[components.length];
		for(int i = 0; i < components.length; i++)
			ss[i] = components[i].size();
		return ss;
	}
	
	void get(StaticMesh mesh, int strip, float[][] vertex);
	
}
