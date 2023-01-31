package com.greentree.engine.moon.opengl.command;

import com.greentree.common.graphics.sgl.vao.GLVertexArray;
import com.greentree.engine.moon.mesh.StaticMesh;

public interface OpenGLContext {
	
	GLVertexArray getVAO(StaticMesh mesh);
	
	void renderBox();
	void renderQuad();
	
}
