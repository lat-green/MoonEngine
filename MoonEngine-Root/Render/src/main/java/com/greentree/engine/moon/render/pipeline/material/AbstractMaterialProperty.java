package com.greentree.engine.moon.render.pipeline.material;


public interface AbstractMaterialProperty extends MaterialProperty {
	
	@Override
	default void setFloat(float x) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void setFloat(float x, float y) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void setFloat(float x, float y, float z) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void setFloat(float x, float y, float z, float w) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void setInt(int x) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void setInt(int x, int y) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void setInt(int x, int y, int z) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void setInt(int x, int y, int z, int w) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void setMatrix4x4f(float m00, float m01, float m02, float m03, float m10, float m11,
			float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31,
			float m32, float m33) {
		throw new UnsupportedOperationException();
	}
	
}
