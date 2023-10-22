package com.greentree.engine.moon.mesh.compoent;

import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.StaticMesh.MeshFace;

public interface StaticMeshFaceComponent extends MeshComponent<StaticMesh> {
	
	@Override
	default void get(StaticMesh mesh, int strip, float[][] vertex) {
		for(int i = 0; i < mesh.size(); i++) {
			final var face = mesh.face(i);
			final var a = vertex[3 * i + 0];
			final var b = vertex[3 * i + 1];
			final var c = vertex[3 * i + 2];
			get(face, strip, a, b, c);
		}
	}
	
	void get(MeshFace face, int strip, float[] a, float[] b, float[] c);
	
	static void setVector(float[] dest, int strip, AbstractVector3f vec) {
		dest[strip + 0] = vec.x();
		dest[strip + 1] = vec.y();
		dest[strip + 2] = vec.z();
	}
	
	static void setVector(float[] dest, int strip, AbstractVector2f vec) {
		dest[strip + 0] = vec.x();
		dest[strip + 1] = vec.y();
	}
	
}
