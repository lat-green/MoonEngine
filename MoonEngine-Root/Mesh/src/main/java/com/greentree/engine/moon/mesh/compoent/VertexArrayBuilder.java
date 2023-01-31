package com.greentree.engine.moon.mesh.compoent;

import java.util.Arrays;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.mesh.AttributeData;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.VertexArrayData;

public final class VertexArrayBuilder {
	
	public VertexArrayBuilder() {
	}
	
	private float[][] get_vertices(StaticMesh mesh, StaticMeshFaceComponent... types) {
		var size = 0;
		for(var type : types)
			size += type.size();
		
		var vertices = new float[mesh.size() * 3][size];
		
		var strip = 0;
		for(var type : types) {
			type.get(mesh, strip, vertices);
			strip += type.size();
		}
		return vertices;
	}
	
	public static float[] union(float[]... arrays) {
		var size = arrays[0].length;
		
		var vertices = new float[size * arrays.length];
		
		for(var i = 0; i < arrays.length; i++)
			for(var j = 0; j < arrays[i].length; j++)
				vertices[i * size + j] = arrays[i][j];
			
		return vertices;
	}
	
	public AttributeData getAttributeGroup(StaticMesh mesh,
			final StaticMeshFaceComponent... types) {
		final var vertices = union(get_vertices(mesh, types));
		return new AttributeData(vertices, MeshComponent.sizes(types));
	}
	
	public VertexArrayData getIndeciesVerticesArray(StaticMesh mesh,
			StaticMeshFaceComponent... types) {
		var ff = get_vertices(mesh, types);
		
		var size = ff[0].length;
		
		final var indecies = new int[ff.length];
		
		var k = 0;
		A :
		for(var i = 0; i < indecies.length; i++) {
			for(var j = 0; j < i; j++)
				if(Arrays.equals(ff[i], ff[j])) {
					indecies[i] = indecies[j];
					ff[i] = null;
					continue A;
				}
			indecies[i] = k++;
		}
		
		final var vertices = new float[k * size];
		
		for(var i = 0; i < indecies.length; i++) {
			if(ff[i] == null)
				continue;
			System.arraycopy(ff[i], 0, vertices, indecies[i] * size, size);
		}
		
		return new VertexArrayData(indecies,
				IteratorUtil.iterable(new AttributeData(vertices, MeshComponent.sizes(types))));
	}
	
	public VertexArrayData getVerticesArray(StaticMesh mesh,
			StaticMeshFaceComponent... components) {
		return new VertexArrayData(null,
				IteratorUtil.iterable(getAttributeGroup(mesh, components)));
	}
	
}
