package com.greentree.engine.moon.mesh.compoent;

import com.greentree.engine.moon.mesh.StaticMesh;
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
	
	@Override
	default void get(StaticMesh mesh, int strip, float[][] vertex) {
		for(int i = 0; i < mesh.size(); i++) {
			final var face = mesh.face(i);
			final var a = vertex[3 * i + 0];
			final var b = vertex[3 * i + 1];
			final var c = vertex[3 * i + 2];
			get(face.a(), strip, a);
			get(face.b(), strip, b);
			get(face.c(), strip, c);
		}
	}
	
}
