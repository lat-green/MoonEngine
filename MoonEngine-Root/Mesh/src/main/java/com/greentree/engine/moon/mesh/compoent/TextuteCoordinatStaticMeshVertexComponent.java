package com.greentree.engine.moon.mesh.compoent;

import com.greentree.engine.moon.mesh.StaticMesh.VertexIndex;


public final class TextuteCoordinatStaticMeshVertexComponent implements StaticMeshVertexComponent {
	
	public static final TextuteCoordinatStaticMeshVertexComponent INSTANCE = new TextuteCoordinatStaticMeshVertexComponent();
	
	private TextuteCoordinatStaticMeshVertexComponent() {
	}
	
	@Override
	public int size() {
		return 2;
	}
	
	@Override
	public void get(VertexIndex index, int strip, float[] dest) {
		if(index.hasTextureCoordinate()) {
			final var a = index.textureCoordinate();
			StaticMeshFaceComponent.setVector(dest, strip, a);
		}else {
			dest[strip + 0] = 0;
			dest[strip + 1] = 0;
		}
	}
	
}
