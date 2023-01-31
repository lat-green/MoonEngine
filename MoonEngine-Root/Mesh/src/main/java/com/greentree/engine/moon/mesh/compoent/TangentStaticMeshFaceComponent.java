package com.greentree.engine.moon.mesh.compoent;

import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.mesh.StaticMesh.MeshFace;


public final class TangentStaticMeshFaceComponent implements StaticMeshFaceComponent {
	
	public static final TangentStaticMeshFaceComponent INSTANCE = new TangentStaticMeshFaceComponent();
	
	private TangentStaticMeshFaceComponent() {
	}
	
	@Override
	public int size() {
		return 3;
	}
	
	@Override
	public void get(MeshFace face, int strip, float[] a, float[] b, float[] c) {
		final var av = face.a().vertex();
		final var bv = face.b().vertex();
		final var cv = face.c().vertex();
		
		final var tex_a = new float[2];
		final var tex_b = new float[2];
		final var tex_c = new float[2];
		TEXTURE_COORDINAT.get(face, 0, tex_a, tex_b, tex_c);
		
		final var at = new Vector2f(tex_a[0], tex_a[1]);
		final var bt = new Vector2f(tex_b[0], tex_b[1]);
		final var ct = new Vector2f(tex_c[0], tex_c[1]);
		
		var arc1 = new Vector3f();
		var arc2 = new Vector3f();
		var deltaUV1 = new Vector2f();
		var deltaUV2 = new Vector2f();
		
		bv.sub(av, arc1);
		cv.sub(av, arc2);
		
		bt.sub(at, deltaUV1);
		ct.sub(at, deltaUV2);
		
		float f = 1.0f / (deltaUV1.x * deltaUV2.y - deltaUV2.x * deltaUV1.y);
		
		var tangent = new Vector3f();
		
		tangent.x = f * (deltaUV2.y * arc1.x - deltaUV1.y * arc2.x);
		tangent.y = f * (deltaUV2.y * arc1.y - deltaUV1.y * arc2.y);
		tangent.z = f * (deltaUV2.y * arc1.z - deltaUV1.y * arc2.z);
		
		tangent.normalize();
		
		StaticMeshFaceComponent.setVector(a, strip, tangent);
		StaticMeshFaceComponent.setVector(b, strip, tangent);
		StaticMeshFaceComponent.setVector(c, strip, tangent);
	}
	
	
}
