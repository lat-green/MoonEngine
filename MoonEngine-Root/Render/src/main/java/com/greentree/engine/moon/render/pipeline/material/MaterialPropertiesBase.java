package com.greentree.engine.moon.render.pipeline.material;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;

import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.math.vector.Vector3f;

public class MaterialPropertiesBase implements MaterialProperties {
	
	protected final Map<String, MaterialProperty> properties = new HashMap<>();
	
	@Override
	public Map<? extends String, ? extends Float> floats() {
		final var result = new HashMap<String, Float>();
		for(var p : properties.entrySet()) {
			if(p.getValue() instanceof float1Property v)
				result.put(p.getKey(), v.x);
		}
		return result;
	}
	
	@Override
	public MaterialProperty get(String name) {
		if(properties.containsKey(name))
			return properties.get(name);
		final var p = newTypeDefinitionProperty(name);
		properties.put(name, p);
		return p;
	}
	
	protected MaterialProperty newTypeDefinitionProperty(String name) {
		return new TypeDefinitionProperty(name);
	}
	
	@Override
	public Map<? extends String, ? extends Integer> ints() {
		final var result = new HashMap<String, Integer>();
		for(var p : properties.entrySet()) {
			if(p.getValue() instanceof int1Property v)
				result.put(p.getKey(), v.x);
		}
		return result;
	}
	
	@Override
	public Map<? extends String, ? extends AbstractVector2f> vec2s() {
		final var result = new HashMap<String, AbstractVector2f>();
		for(var p : properties.entrySet()) {
			if(p.getValue() instanceof float2Property v)
				result.put(p.getKey(), new Vector2f(v.x, v.y));
		}
		return result;
	}
	
	@Override
	public Map<? extends String, ? extends AbstractVector3f> vec3s() {
		final var result = new HashMap<String, AbstractVector3f>();
		for(var p : properties.entrySet()) {
			if(p.getValue() instanceof float3Property v)
				result.put(p.getKey(), new Vector3f(v.x, v.y, v.z));
		}
		return result;
	}
	
	private final class TypeDefinitionProperty implements AbstractMaterialProperty {
		
		private final String name;
		
		public TypeDefinitionProperty(String name) {
			this.name = name;
		}
		
		@Override
		public void setFloat(float x) {
			final var p = new float1Property();
			p.setFloat(x);
			properties.put(name, p);
		}
		
		@Override
		public void setFloat(float x, float y) {
			final var p = new float2Property();
			p.setFloat(x, y);
			properties.put(name, p);
		}
		
		@Override
		public void setFloat(float x, float y, float z) {
			final var p = new float3Property();
			p.setFloat(x, y, z);
			properties.put(name, p);
		}
		
		
		@Override
		public void setInt(int x) {
			final var p = new int1Property();
			p.setInt(x);
			properties.put(name, p);
		}
		
		@Override
		public void setMatrix4x4f(float m00, float m01, float m02, float m03, float m10, float m11,
				float m12, float m13, float m20, float m21, float m22, float m23, float m30,
				float m31, float m32, float m33) {
			final var p = new mat4Property();
			p.setMatrix4x4f(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31,
					m32, m33);
			properties.put(name, p);
		}
		
	}
	
	private static final class int1Property implements AbstractMaterialProperty {
		
		private int x;
		
		@Override
		public void setInt(int x) {
			this.x = x;
		}
		
	}
	
	private static final class float1Property implements AbstractMaterialProperty {
		
		private float x;
		
		@Override
		public void setFloat(float x) {
			this.x = x;
		}
		
		
	}
	
	private static final class float2Property implements AbstractMaterialProperty {
		
		private float x;
		private float y;
		
		@Override
		public void setFloat(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}
	
	private static final class float3Property implements AbstractMaterialProperty {
		
		private float x;
		private float y;
		private float z;
		
		@Override
		public void setFloat(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
	}
	
	private static final class mat4Property implements AbstractMaterialProperty {
		
		private float m00, m01, m02, m03;
		private float m10, m11, m12, m13;
		private float m20, m21, m22, m23;
		private float m30, m31, m32, m33;
		
		@Override
		public void setMatrix4x4f(float m00, float m01, float m02, float m03, float m10, float m11,
				float m12, float m13, float m20, float m21, float m22, float m23, float m30,
				float m31, float m32, float m33) {
			this.m00 = m00;
			this.m01 = m01;
			this.m02 = m02;
			this.m03 = m03;
			
			this.m10 = m10;
			this.m11 = m11;
			this.m12 = m12;
			this.m13 = m13;
			
			this.m20 = m20;
			this.m21 = m21;
			this.m22 = m22;
			this.m23 = m23;
			
			this.m30 = m30;
			this.m31 = m31;
			this.m32 = m32;
			this.m33 = m33;
		}
		
		public Matrix4f matrix() {
			return new Matrix4f(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30,
					m31, m32, m33);
		}
		
	}
	
	@Override
	public Map<? extends String, ? extends Matrix4f> mat4s() {
		final var result = new HashMap<String, Matrix4f>();
		for(var p : properties.entrySet()) {
			if(p.getValue() instanceof mat4Property v)
				result.put(p.getKey(), v.matrix());
		}
		return result;
	}
	
}
