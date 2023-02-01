package com.greentree.engine.moon.mesh;

import java.util.ArrayList;
import java.util.List;

import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;

public final class GraphicsMesh implements StaticMesh {
	
	
	private static final long serialVersionUID = 1L;
	
	private final List<? extends AbstractVector3f> vertices;
	
	private final List<? extends AbstractVector2f> textureCoords;
	private final List<? extends AbstractVector3f> normals;
	private final List<MeshFace> faces;
	
	public GraphicsMesh(final List<? extends AbstractVector3f> vertices,
			final List<? extends AbstractVector2f> textureCoords,
			final List<? extends AbstractVector3f> normals, List<MeshFace> faces) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.faces = faces;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	@Override
	public MeshFace face(int i) {
		return faces.get(i);
	}
	
	@Override
	public boolean hasNormals() {
		return !normals.isEmpty();
	}
	
	
	@Override
	public boolean hasTextureCoord() {
		return !textureCoords.isEmpty();
	}
	
	public int length() {
		return faces.size() * 3;
	}
	
	@Override
	public int size() {
		return faces.size();
	}
	
	public static class Builder {
		
		private final List<AbstractVector3f> vertices;
		private final List<AbstractVector2f> textureCoords;
		private final List<AbstractVector3f> normals;
		private final List<MeshFace> faces;
		private final GraphicsMesh mesh;
		
		public Builder() {
			vertices = new ArrayList<>();
			textureCoords = new ArrayList<>();
			faces = new ArrayList<>();
			normals = new ArrayList<>();
			mesh = new GraphicsMesh(vertices, textureCoords, normals, faces);
		}
		
		public void addFaces(AbstractVector3f a, AbstractVector3f b, AbstractVector3f c) {
			addFaces(new ValueVertexIndex(a), new ValueVertexIndex(b), new ValueVertexIndex(c));
		}
		
		public void addFaces(ValueVertexIndex a, ValueVertexIndex b, ValueVertexIndex c) {
			final var av = add(vertices, a.vertex());
			final var bv = add(vertices, b.vertex());
			final var cv = add(vertices, c.vertex());
			
			final var an = add(normals, a.vertex());
			final var bn = add(normals, b.vertex());
			final var cn = add(normals, c.vertex());
			
			final var at = add(textureCoords, a.textureCoordinate());
			final var bt = add(textureCoords, b.textureCoordinate());
			final var ct = add(textureCoords, c.textureCoordinate());
			
			final var ai = mesh.new MeshVertexIndex(av, an, at);
			final var bi = mesh.new MeshVertexIndex(bv, bn, bt);
			final var ci = mesh.new MeshVertexIndex(cv, cn, ct);
			
			faces.add(new MeshFace(ai, bi, ci));
		}
		
		private static <E> int add(List<E> list, E e) {
			if(e == null)
				return -1;
			int index = list.indexOf(e);
			if(index >= 0)
				return index;
			list.add(e);
			return list.size() - 1;
		}
		
		public GraphicsMesh build() {
			return mesh;
		}
		
	}
	
	public final class MeshVertexIndex implements VertexIndex {
		
		private static final long serialVersionUID = 1L;
		private final int vertex;
		private final int normal;
		private final int texCoord;
		
		public MeshVertexIndex(int vertex, int normal, int texCoord) {
			this.vertex = vertex;
			this.normal = normal;
			this.texCoord = texCoord;
		}
		
		@Override
		public boolean hasNormal() {
			return normal >= 0 && VertexIndex.super.hasNormal();
		}
		
		@Override
		public boolean hasTextureCoordinate() {
			return texCoord >= 0 && VertexIndex.super.hasTextureCoordinate();
		}
		
		@Override
		public AbstractVector3f normal() {
			return normals.get(normal);
		}
		
		@Override
		public AbstractVector2f textureCoordinate() {
			return textureCoords.get(texCoord);
		}
		
		@Override
		public AbstractVector3f vertex() {
			return vertices.get(vertex);
		}
		
	}
	
}
