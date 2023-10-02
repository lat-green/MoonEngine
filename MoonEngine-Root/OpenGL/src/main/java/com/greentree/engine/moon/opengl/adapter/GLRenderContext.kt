package com.greentree.engine.moon.opengl.adapter

import com.greentree.commons.graphics.smart.RenderContext
import com.greentree.commons.graphics.smart.mesh.Mesh
import com.greentree.commons.graphics.smart.shader.Shader
import com.greentree.commons.graphics.smart.target.FrameBuffer
import com.greentree.engine.moon.render.MaterialUtil
import com.greentree.engine.moon.render.mesh.MeshUtil
import com.greentree.engine.moon.render.pipeline.RenderLibrary

class GLRenderContext(val library: RenderLibrary) : RenderContext {

	override fun getDefaultCubeMapShadowShader(): Shader {
		return library.build(MaterialUtil.getDefaultCubeMapShadowShader())
	}

	override fun getDefaultMeshBox(): Mesh {
		return library.build(MeshUtil.BOX)
	}

	override fun getDefaultMeshQuad(): Mesh {
		return library.build(MeshUtil.QUAD)
	}

	override fun getDefaultMeshQuadSprite(): Mesh {
		return library.build(MeshUtil.QUAD_SPRITE)
	}

	override fun getDefaultShadowShader(): Shader {
		return library.build(MaterialUtil.getDefaultShadowShader())
	}

	override fun getDefaultSkyBoxShader(): Shader {
		return library.build(MaterialUtil.getDefaultSkyBoxShader())
	}

	override fun getDefaultSpriteShader(): Shader {
		return library.build(MaterialUtil.getDefaultSpriteShader())
	}

	override fun getDefaultTextureShader(): Shader {
		return library.build(MaterialUtil.getDefaultTextureShader())
	}

	override fun newFrameBuffer(): FrameBuffer.Builder {
		return library.createRenderTarget()
	}

	override fun newMesh(): Mesh.Builder {
		TODO()
	}
}