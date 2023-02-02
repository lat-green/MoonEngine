package com.greentree.engine.moon.render.pipeline.material;

public interface PropertyLocation {
	
	void setFloat(float x);
	
	void setFloat(float x, float y);
	
	void setFloat(float x, float y, float z);
	
	void setFloat(float x, float y, float z, float w);
	
	void setInt(int x);
	
	void setInt(int x, int y);
	
	void setInt(int x, int y, int z);
	
	void setInt(int x, int y, int z, int w);
	
	void setMatrix4x4f(float m00, float m01, float m02, float m03, float m10, float m11, float m12,
			float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32,
			float m33);
	
}
