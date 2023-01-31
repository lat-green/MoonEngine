package com.greentree.engine.moon.mesh.compoent;

import com.greentree.engine.moon.mesh.StaticMesh.VertexIndex;


public final class VertexStaticMeshVertexComponent implements StaticMeshVertexComponent {
	
	public static final VertexStaticMeshVertexComponent INSTANCE = new VertexStaticMeshVertexComponent();
	
	private VertexStaticMeshVertexComponent() {
	}
	
	@Override
	public int size() {
		return 3;
	}
	
	@Override
	public void get(VertexIndex index, int strip, float[] dest) {
		final var vertex = index.vertex();
		StaticMeshFaceComponent.setVector(dest, strip, vertex);
	}
	
}
