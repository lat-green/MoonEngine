package com.greentree.engine.moon.mesh.compoent;

import com.greentree.engine.moon.mesh.StaticMesh.MeshFace;
import com.greentree.engine.moon.mesh.StaticMesh.VertexIndex;

public interface StaticMeshVertexComponent extends StaticMeshFaceComponent {
	
	@Override
	default void get(MeshFace face, int strip, float[] a, float[] b, float[] c) {
		get(face.a(), strip, a);
		get(face.b(), strip, b);
		get(face.c(), strip, c);
	}
	
	void get(VertexIndex index, int strip, float[] dest);
}
