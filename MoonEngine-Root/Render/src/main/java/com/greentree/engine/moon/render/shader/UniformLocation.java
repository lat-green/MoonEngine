package com.greentree.engine.moon.render.shader;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;

import com.greentree.commons.image.Color;
import com.greentree.commons.math.float3;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;

public interface UniformLocation {
	
	
	void rgb(Color color);
	
	void rgba(Color color);
	
	void set4fv(FloatBuffer matrix);
	
	void set4fv(int offset, FloatBuffer matrix);
	void setf(AbstractVector2f f);
	void setf(AbstractVector3f f);
	
	void setf(float x);
	void setf(float x, float y);
	void setf(float x, float y, float z);
	void setf(float x, float y, float z, float w);
	void setf(float3 f);
	void setfi(int offset, float x);
	void setfi(int offset, float x, float y);
	
	void setfi(int offset, float x, float y, float z);
	void setfi(int offset, float x, float y, float z, float w);
	
	void seti(int i);
	
	void set4fv(Matrix4f matrix4f);
	
}
