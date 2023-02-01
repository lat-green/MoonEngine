package com.greentree.engine.moon.opengl;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.SGLFW;
import com.greentree.common.graphics.sgl.buffer.FloatStaticDrawArrayBuffer;
import com.greentree.common.graphics.sgl.enums.gl.GLShaderType;
import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.common.graphics.sgl.shader.GLUniformLocation;
import com.greentree.common.graphics.sgl.vao.GLVertexArray;
import com.greentree.common.graphics.sgl.vao.GLVertexArray.AttributeGroup;
import com.greentree.common.graphics.sgl.window.Window;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent;
import com.greentree.engine.moon.render.MeshUtil;

public class BasicMeshView {
	
	private static final String VERTEX = """
#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normal;

uniform mat4 model;

out vec3 fNormal;

void main()
{
	fNormal = mat3(model) * normal;
	gl_Position = model * vec4(position, 1.0);
}
				""";
	private static final String FRAGMENT = """
#version 330 core

in vec3 fNormal;

out vec4 color;

void main()
{
	color = vec4(abs(fNormal), 1.0f);
}
						""";
	
	public static void main(String[] args) {
		view(MeshUtil.BOX);
		view(MeshUtil.QUAD);
	}
	
	private static GLShaderProgram program() {
		try(final var vert = new GLSLShader(VERTEX, GLShaderType.VERTEX);
				final var frag = new GLSLShader(FRAGMENT, GLShaderType.FRAGMENT);) {
			return new GLShaderProgram(List.of(vert, frag));
		}
	}
	
	private static void view(StaticMesh mesh) {
		SGLFW.init();
		try(final var window = new Window("BasicMeshView", 800, 600)) {
			window.makeCurrent();
			
			final var prog = program();
			prog.bind();
			
			glClearColor(.6f, .6f, .6f, 1);
			
			final var vao = vao(mesh);
			vao.bind();
			Matrix4f model = new Matrix4f();
			
			glEnable(GL_DEPTH_TEST);
			glEnable(GL_CULL_FACE);
			
			while(!window.isShouldClose()) {
				Window.updateEvents();
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				
				model.rotate(.0001f, 0, 1, 0);
				model.rotate(.00003f, 0, 0, 1);
				set(prog.getUL("model"), model);
				
				glDrawArrays(GL_TRIANGLES, 0, vao.size());
				
				window.swapBuffer();
			}
		}
		SGLFW.terminate();
	}
	
	private static void set(GLUniformLocation ul, Matrix4f model) {
		try(final var stack = MemoryStack.create().push()) {
			final var buffer = stack.mallocFloat(16);
			model.get(buffer);
			ul.set4fv(buffer);
		}
	}
	
	private static GLVertexArray vao(StaticMesh mesh) {
		final var v = mesh.getAttributeGroup(StaticMeshFaceComponent.VERTEX,
				StaticMeshFaceComponent.NORMAL);
		
		final var VERTEXS = v.vertex();
		final var vbo = new FloatStaticDrawArrayBuffer();
		vbo.bind();
		try(final var stack = MemoryStack.create(VERTEXS.length * GLType.FLOAT.size).push()) {
			vbo.setData(stack.floats(VERTEXS));
		}
		vbo.unbind();
		final var vao = new GLVertexArray(AttributeGroup.of(vbo, v.sizes()));
		return vao;
	}
	
}
