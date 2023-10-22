package com.greentree.engine.moon.mesh;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.mesh.compoent.StaticMeshVertexComponent;
import com.greentree.engine.moon.mesh.compoent.VertexArrayBuilder;

public class MeshTest {
	
	StaticMesh plane;
	
	@BeforeEach
	void init_plane() {
		final var builder = GraphicsMesh.builder();
		final var v1 = new Vector3f(1, 0, 0);
		final var v2 = new Vector3f(1, 1, 0);
		final var v3 = new Vector3f(0, 1, 0);
		final var v4 = new Vector3f(0, 0, 0);
		
		builder.addFaces(v1, v2, v3);
		builder.addFaces(v3, v4, v1);
		
		plane = builder.build();
	}
	
	@Test
	void test1() {
		final var builder = new VertexArrayBuilder();
		
		final var group = builder.getIndeciesVerticesArray(plane, StaticMeshVertexComponent.VERTEX);
		
		System.out.println(group);
	}
	
}
