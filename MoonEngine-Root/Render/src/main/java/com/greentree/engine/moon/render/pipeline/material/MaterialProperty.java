package com.greentree.engine.moon.render.pipeline.material;

import org.joml.Matrix4f;

import com.greentree.commons.image.Color;
import com.greentree.commons.math.vector.AbstractVector2f;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.engine.moon.render.texture.Texture;

public interface MaterialProperty {
	
	void setTexture(Texture texture);
	
	default void setFloat(AbstractVector2f vector) {
		setFloat(vector.x(), vector.y());
	}
	
	default void setFloat(AbstractVector3f vector) {
		setFloat(vector.x(), vector.y(), vector.z());
	}
	
	default void setFloat(Color color) {
		setFloat(color.r, color.g, color.b);
	}
	
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
	
	default void setMatrix4x4f(Matrix4f matrix) {
		setMatrix4x4f(matrix.m00(), matrix.m01(), matrix.m02(), matrix.m03(), matrix.m10(),
				matrix.m11(), matrix.m12(), matrix.m13(), matrix.m20(), matrix.m21(), matrix.m22(),
				matrix.m23(), matrix.m30(), matrix.m31(), matrix.m32(), matrix.m33());
	}
	
}
