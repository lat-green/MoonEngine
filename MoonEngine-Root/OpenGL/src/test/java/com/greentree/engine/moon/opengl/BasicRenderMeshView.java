package com.greentree.engine.moon.opengl;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.SGLFW;
import com.greentree.common.graphics.sgl.Window;
import com.greentree.common.graphics.sgl.enums.gl.GLShaderType;
import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.common.graphics.sgl.shader.GLUniformLocation;
import com.greentree.commons.util.time.DeltaTimer;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.opengl.adapter.GLRenderLibrary;
import com.greentree.engine.moon.render.mesh.MeshUtil;

public class BasicRenderMeshView {
	
	private static final String VERTEX = """
#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normal;
layout (location = 2) in vec3 tangent;

uniform mat4 model;

out vec3 fNormal;

void main()
{
	fNormal = normal;
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
	//color = vec4(vec3(dot(vec3(0,1,0), fNormal)), 1);
}
						""";
	
	public static void main(String[] args) throws InterruptedException {
		//		view(MeshUtil.QUAD);
		view(MeshUtil.BOX);
	}
	
	private static GLShaderProgram program() {
		try(final var vert = new GLSLShader(VERTEX, GLShaderType.VERTEX);
				final var frag = new GLSLShader(FRAGMENT, GLShaderType.FRAGMENT);) {
			return new GLShaderProgram(List.of(vert, frag));
		}
	}
	
	private static void view(StaticMesh mesh) throws InterruptedException {
		SGLFW.init();
		final var timer = new DeltaTimer();
		try(final var window = new Window("BasicRenderMeshView", 800, 600)) {
			window.makeCurrent();
			
			final var LIBRARY = new GLRenderLibrary();
			
			try(final var prog = program();) {
				prog.bind();
				
				glClearColor(.6f, .6f, .6f, 1);
				
				final var m = LIBRARY.build(mesh);
				Matrix4f model = new Matrix4f();
				
				glEnable(GL_DEPTH_TEST);
				glEnable(GL_CULL_FACE);
				timer.step();
				
				while(!window.isShouldClose()) {
					Window.updateEvents();
					glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
					
					timer.step();
					final var depth = timer.getDelta();
					
					Thread.sleep(10);
					
					System.out.println(1f / depth);
					
					model.rotate(.1f * depth, 0, 1, 0);
					model.rotate(.03f * depth, 0, 0, 1);
					set(prog.getUL("model"), model);
					
					m.render();
					
					window.swapBuffer();
				}
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
	
}
