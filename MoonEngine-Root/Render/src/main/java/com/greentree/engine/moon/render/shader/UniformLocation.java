package com.greentree.engine.moon.render.shader;

public interface UniformLocation {
	
	default void setFloat(float x) {
		setOffsetFloat(0, x);
	}
	
	default void setFloat(float x, float y) {
		setOffsetFloat(0, x, y);
	}
	
	default void setFloat(float x, float y, float z) {
		setOffsetFloat(0, x, y, z);
	}
	
	default void setFloat(float x, float y, float z, float w) {
		setOffsetFloat(0, x, y, z, w);
	}
	
	void setOffsetFloat(int offset, float x);
	void setOffsetFloat(int offset, float x, float y);
	void setOffsetFloat(int offset, float x, float y, float z);
	void setOffsetFloat(int offset, float x, float y, float z, float w);
	
	default void setInt(int x) {
		setOffsetInt(0, x);
	}
	
	default void setInt(int x, int y) {
		setOffsetInt(0, x, y);
	}
	
	default void setInt(int x, int y, int z) {
		setOffsetInt(0, x, y, z);
	}
	
	default void setInt(int x, int y, int z, int w) {
		setOffsetInt(0, x, y, z, w);
	}
	
	void setOffsetInt(int offset, int x);
	void setOffsetInt(int offset, int x, int y);
	void setOffsetInt(int offset, int x, int y, int z);
	void setOffsetInt(int offset, int x, int y, int z, int w);
	
	default void setMatrix4x4f(float m00, float m01, float m02, float m03, float m10, float m11,
			float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31,
			float m32, float m33) {
		setOffsetMatrix4x4f(0, m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31,
				m32, m33);
	}
	
	void setOffsetMatrix4x4f(int offset, float m00, float m01, float m02, float m03, float m10,
			float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30,
			float m31, float m32, float m33);
	
}
