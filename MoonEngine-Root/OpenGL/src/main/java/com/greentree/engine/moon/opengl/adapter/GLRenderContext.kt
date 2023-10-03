package com.greentree.engine.moon.opengl.adapter

import com.greentree.common.graphics.sgl.buffer.FloatStaticDrawArrayBuffer
import com.greentree.common.graphics.sgl.enums.gl.GLType
import com.greentree.common.graphics.sgl.shader.GLSLShader
import com.greentree.common.graphics.sgl.shader.GLShaderProgram
import com.greentree.common.graphics.sgl.vao.GLVertexArray
import com.greentree.commons.graphics.smart.RenderContext
import com.greentree.commons.graphics.smart.VideoBuffer
import com.greentree.commons.graphics.smart.mesh.Mesh
import com.greentree.commons.graphics.smart.shader.Shader
import com.greentree.commons.graphics.smart.target.FrameBuffer
import com.greentree.commons.graphics.smart.target.RenderCommandBuffer
import com.greentree.commons.graphics.smart.target.RenderTarget
import com.greentree.commons.util.iterator.IteratorUtil
import com.greentree.engine.moon.mesh.AttributeData
import com.greentree.engine.moon.mesh.StaticMesh
import com.greentree.engine.moon.mesh.compoent.MeshComponent
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent
import com.greentree.engine.moon.opengl.GLEnums
import com.greentree.engine.moon.opengl.adapter.buffer.PushCommandBuffer
import com.greentree.engine.moon.render.MaterialUtil
import com.greentree.engine.moon.render.mesh.MeshUtil
import com.greentree.engine.moon.render.shader.ShaderData
import com.greentree.engine.moon.render.shader.ShaderProgramData
import org.lwjgl.system.MemoryStack
import java.util.*

class GLRenderContext : RenderContext, RenderTarget {

	override fun buffer(): RenderCommandBuffer {
		return PushCommandBuffer(this)
	}

	fun build(mesh: StaticMesh): RenderMesh {
		return build(mesh.getAttributeGroup(*COMPONENTS))
	}

	fun build(mesh: AttributeData): RenderMesh {
		val vao: GLVertexArray = getVAO(mesh)
		return VAORenderMeshAdapter(vao)
	}

	fun getVAO(mesh: AttributeData): GLVertexArray {
		return MESH_BUILDER.getVAO(mesh)
	}

	fun build(program: ShaderProgramData): Shader {
		return SHADER_BUILDER.build(program)
	}

	data class TArray<T>(val array: Array<T>) {

		override fun equals(obj: Any?): Boolean {
			if(this === obj) return true
			return if(obj !is TArray<*>) false else Arrays.deepEquals(
				array,
				obj.array
			)
		}

		override fun hashCode(): Int {
			val prime = 31
			var result = 1
			result = prime * result + Arrays.deepHashCode(array)
			return result
		}
	}

	override fun getDefaultCubeMapShadowShader(): Shader {
		return build(MaterialUtil.getDefaultCubeMapShadowShader())
	}

	override fun getDefaultMeshBox(): Mesh {
		return build(MeshUtil.BOX)
	}

	override fun getDefaultMeshQuad(): Mesh {
		return build(MeshUtil.QUAD)
	}

	override fun getDefaultMeshQuadSprite(): Mesh {
		return build(MeshUtil.QUAD_SPRITE)
	}

	override fun getDefaultShadowShader(): Shader {
		return build(MaterialUtil.getDefaultShadowShader())
	}

	override fun getDefaultSkyBoxShader(): Shader {
		return build(MaterialUtil.getDefaultSkyBoxShader())
	}

	override fun getDefaultSpriteShader(): Shader {
		return build(MaterialUtil.getDefaultSpriteShader())
	}

	override fun getDefaultTextureShader(): Shader {
		return build(MaterialUtil.getDefaultTextureShader())
	}

	override fun newBindingPoint(size: Int) = OpenGLBindingPoint(size.toLong())

	override fun newFrameBuffer(): FrameBuffer.Builder {
		return GLRenderTargetTextuteBuilder(this)
	}

	override fun newMesh(): Mesh.Builder {
		TODO()
	}

	override fun newVideoBuffer(): VideoBuffer.Builder {
		TODO("Not yet implemented")
	}

	private class RenderMeshBuilder {

		private val vaos: MutableMap<AttributeData, GLVertexArray> = HashMap<AttributeData, GLVertexArray>()
		fun getVAO(attribute: AttributeData) = vaos.getOrPut(attribute) {
			val vbo = getVBO(attribute.vertex())
			val vao = GLVertexArray(GLVertexArray.AttributeGroup.of(vbo, *attribute.sizes()))
			vao
		}

		companion object {

			fun getVBO(VERTEXS: FloatArray): FloatStaticDrawArrayBuffer {
				val vbo = FloatStaticDrawArrayBuffer()
				vbo.bind()
				MemoryStack.create(VERTEXS.size * GLType.FLOAT.size).push()
					.use { stack -> vbo.setData(stack.floats(*VERTEXS)) }
				vbo.unbind()
				return vbo
			}
		}
	}

	private class GLSLShaderBuilder {

		private val programs: MutableMap<ShaderProgramData, Shader> = HashMap<ShaderProgramData, Shader>()

		fun build(program: ShaderProgramData) = programs.getOrPut(program) {
			val shader: Shader
			val vert: GLSLShader = build(program.vert())
			val frag: GLSLShader = build(program.frag())
			shader = if(program.geom() != null) {
				val geom: GLSLShader = build(program.geom())
				val p = GLShaderProgram(IteratorUtil.iterable(vert, frag, geom))
				OpenGLShader(p)
			} else {
				val p = GLShaderProgram(IteratorUtil.iterable(vert, frag))
				OpenGLShader(p)
			}
			shader
		}

		private fun build(shader: ShaderData): GLSLShader {
			return GLSLShader(shader.text(), GLEnums.get(shader.type()))
		}
	}

	companion object {

		val COMPONENTS: Array<StaticMeshFaceComponent> = arrayOf(
			MeshComponent.VERTEX, MeshComponent.NORMAL, MeshComponent.TEXTURE_COORDINAT, MeshComponent.TANGENT
		)
		private val MESH_BUILDER = RenderMeshBuilder()
		private val SHADER_BUILDER = GLSLShaderBuilder()
	}
}