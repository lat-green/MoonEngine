package com.greentree.engine.moon.render.camera;

import java.io.Serializable;

import org.joml.Matrix4f;

public interface ProjectionMatrix extends Serializable {

	Matrix4f getMatrix(float width, float height);
	
}
