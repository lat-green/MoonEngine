package com.greentree.engine.moon.mesh;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;

import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.engine.moon.mesh.StaticMesh.MeshFace;

public interface StaticMesh extends Mesh, Iterable<MeshFace>, Serializable {
	
	MeshFace face(int i);
	
	@Override
	default Iterator<MeshFace> iterator() {
		return new Iterator<>() {
			
			int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < size();
			}
			
			@Override
			public MeshFace next() {
				return face(index++);
			}
			
		};
	}
	
	int size();
	
	public record MeshFace(VertexIndex a, VertexIndex b, VertexIndex c) implements Serializable {
		
		public MeshFace {
			Objects.requireNonNull(a);
			Objects.requireNonNull(b);
			Objects.requireNonNull(c);
		}
		
		public boolean hasNormals() {
			if(!a.hasNormal() || !b.hasNormal() || !c.hasNormal())
				return false;
			return true;
		}
		
		public boolean hasTextureCoordinate() {
			if(!a.hasTextureCoordinate() || !b.hasTextureCoordinate() || !c.hasTextureCoordinate())
				return false;
			return true;
		}
		
		@Override
		public String toString() {
			return "Face [" + a + ", " + b + ", " + c + "]";
		}
		
	}
	
	public record ValueVertexIndex(AbstractVector3f vertex, AbstractVector3f normal,
			AbstractVector2f textureCoordinate) implements VertexIndex {
		
		public ValueVertexIndex {
			Objects.requireNonNull(vertex);
		}
		
		
		public ValueVertexIndex(AbstractVector3f vertex) {
			this(vertex, null, null);
		}
		
	}
	
	public interface VertexIndex extends Serializable {
		
		default boolean hasNormal() {
			return normal() != null;
		}
		
		default boolean hasTextureCoordinate() {
			return textureCoordinate() != null;
		}
		
		AbstractVector3f normal();
		AbstractVector2f textureCoordinate();
		AbstractVector3f vertex();
		
	}
	
}
