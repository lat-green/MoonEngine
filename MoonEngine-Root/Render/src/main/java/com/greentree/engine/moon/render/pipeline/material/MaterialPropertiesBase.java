package com.greentree.engine.moon.render.pipeline.material;

import java.util.HashMap;
import java.util.Map;

import com.greentree.engine.moon.render.texture.Texture;

public final class MaterialPropertiesBase implements MaterialProperties {
	
	protected final Map<String, Property> properties = new HashMap<>();
	
	@Override
	public String toString() {
		return "MaterialPropertiesBase " + properties;
	}
	
	@Override
	public void put(String name, float x) {
		properties.put(name, new float1Property(x));
	}
	
	@Override
	public void put(String name, float x, float y) {
		properties.put(name, new float2Property(x, y));
	}
	
	@Override
	public void put(String name, float x, float y, float z) {
		properties.put(name, new float3Property(x, y, z));
	}
	
	@Override
	public void put(String name, int x) {
		properties.put(name, new int1Property(x));
	}
	
	@Override
	public void put(String name, float m00, float m01, float m02, float m03, float m10, float m11,
			float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31,
			float m32, float m33) {
		properties.put(name, new mat4Property(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22,
				m23, m30, m31, m32, m33));
	}
	
	@Override
	public void put(String name, Texture texture) {
		properties.put(name, new TextureProperty(texture));
	}
	
	@Override
	public void set(Shader shader) {
		final var context = new PropertyBindContext() {
			
			int nextEmptyTextureSlot = 1;
			
			@Override
			public int nextEmptyTextureSlot() {
				return nextEmptyTextureSlot++;
			}
			
		};
		for(var entry : properties.entrySet()) {
			final var name = entry.getKey();
			final var property = entry.getValue();
			final var location = shader.getProperty(name);
			property.bind(location, context);
		}
	}
	
	private interface Property {
		
		void bind(PropertyLocation location, PropertyBindContext context);
		
	}
	
	private interface PropertyBindContext {
		
		int nextEmptyTextureSlot();
		
	}
	
	private record TextureProperty(Texture texture) implements Property {
		
		@Override
		public void bind(PropertyLocation property, PropertyBindContext context) {
			final var slot = context.nextEmptyTextureSlot();
			property.setInt(slot);
			texture.bint(slot);
		}
		
	}
	
	private record float1Property(float x) implements Property {
		
		@Override
		public void bind(PropertyLocation property, PropertyBindContext context) {
			property.setFloat(x);
		}
		
	}
	
	private record float2Property(float x, float y) implements Property {
		
		@Override
		public void bind(PropertyLocation property, PropertyBindContext context) {
			property.setFloat(x, y);
		}
	}
	
	private record float3Property(float x, float y, float z) implements Property {
		
		@Override
		public void bind(PropertyLocation property, PropertyBindContext context) {
			property.setFloat(x, y, z);
		}
		
	}
	
	private record int1Property(int x) implements Property {
		
		@Override
		public void bind(PropertyLocation property, PropertyBindContext context) {
			property.setInt(x);
		}
		
	}
	
	private record mat4Property(float m00, float m01, float m02, float m03, float m10, float m11,
			float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31,
			float m32, float m33) implements Property {
		
		@Override
		public void bind(PropertyLocation property, PropertyBindContext context) {
			property.setMatrix4x4f(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30,
					m31, m32, m33);
		}
		
	}
	
}
