package com.greentree.engine.moon.mesh.compoent;

import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.mesh.StaticMesh.MeshFace;
import com.greentree.engine.moon.mesh.StaticMesh.VertexIndex;


public final class NormalStaticMeshVertexComponent implements StaticMeshVertexComponent {
	
	public static final NormalStaticMeshVertexComponent INSTANCE = new NormalStaticMeshVertexComponent();
	
	private NormalStaticMeshVertexComponent() {
	}
	
	@Override
	public void get(MeshFace face, int strip, float[] a, float[] b, float[] c) {
		if(face.hasNormals())
			StaticMeshVertexComponent.super.get(face, strip, a, b, c);
		else {
			var va = face.a().vertex();
			var vb = face.b().vertex();
			var vc = face.c().vertex();
			
			final var normal = va.sub(vb, new Vector3f()).cross(vc.sub(vb, new Vector3f()))
					.normalize(-1);
			
			StaticMeshFaceComponent.setVector(a, strip, normal);
			StaticMeshFaceComponent.setVector(b, strip, normal);
			StaticMeshFaceComponent.setVector(c, strip, normal);
		}
	}
	
	@Override
	public void get(VertexIndex index, int strip, float[] dest) {
		if(!index.hasNormal())
			throw new UnsupportedOperationException();
		StaticMeshFaceComponent.setVector(dest, strip, index.normal());
	}
	
	@Override
	public int size() {
		return 3;
	}
	
}
