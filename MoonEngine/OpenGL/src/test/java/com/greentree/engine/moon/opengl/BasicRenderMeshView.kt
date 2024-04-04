package com.greentree.engine.moon.opengl

import com.greentree.common.graphics.sgl.OpenGLContext
import com.greentree.common.graphics.sgl.Window
import com.greentree.common.graphics.sgl.enums.gl.GLShaderType
import com.greentree.common.graphics.sgl.shader.GLSLShader
import com.greentree.common.graphics.sgl.shader.GLShaderProgram
import com.greentree.common.graphics.sgl.shader.GLUniformLocation
import com.greentree.common.graphics.sglfw.SimpleGLFW.glfwPollEvents
import com.greentree.commons.util.time.DeltaTimer
import com.greentree.engine.moon.mesh.StaticMesh
import com.greentree.engine.moon.opengl.adapter.GLRenderContext
import com.greentree.engine.moon.render.mesh.MeshUtil
import org.joml.Matrix4f
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import java.util.List

object BasicRenderMeshView {

	private val VERTEX = """
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
            
            """.trimIndent()
	private val FRAGMENT = """
            #version 330 core

            in vec3 fNormal;

            out vec4 color;

            void main()
            {
            color = vec4(abs(fNormal), 1.0f);
            //color = vec4(vec3(dot(vec3(0,1,0), fNormal)), 1);
            }
            
            """.trimIndent()

	@Throws(InterruptedException::class)
	@JvmStatic
	fun main(args: Array<String>) {
		view(MeshUtil.QUAD)
		//		view(MeshUtil.BOX);
	}

	@Throws(InterruptedException::class)
	private fun view(mesh: StaticMesh) {
		val timer = DeltaTimer()
		Window("BasicRenderMeshView", 800, 600).use { window ->
			window.makeCurrent()
			val LIBRARY = GLRenderContext()
			val prog = program()
			prog.bind()
			GL11.glClearColor(.6f, .6f, .6f, 1f)
			val m = LIBRARY.build(mesh)
			val model = Matrix4f()
			GL11.glEnable(GL11.GL_DEPTH_TEST)
			GL11.glEnable(GL11.GL_CULL_FACE)
			timer.step()
			while(!window.isShouldClose) {
				glfwPollEvents()
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
				timer.step()
				val depth = timer.delta
				Thread.sleep(10)
				println(1f / depth)
				model.rotate(.1f * depth, 0f, 1f, 0f)
				model.rotate(.03f * depth, 0f, 0f, 1f)
				BasicRenderMeshView[prog.getUL("model")] = model
				m.render()
				window.swapBuffer()
			}
			OpenGLContext.unmakeCurrent()
		}
	}

	private fun program(): GLShaderProgram {
		val vert = GLSLShader(VERTEX, GLShaderType.VERTEX)
		val frag = GLSLShader(FRAGMENT, GLShaderType.FRAGMENT)
		return GLShaderProgram(List.of(vert, frag))
	}

	private operator fun set(ul: GLUniformLocation, model: Matrix4f) {
		MemoryStack.create().push().use { stack ->
			val buffer = stack.mallocFloat(16)
			model[buffer]
			ul.set4fv(buffer)
		}
	}
}