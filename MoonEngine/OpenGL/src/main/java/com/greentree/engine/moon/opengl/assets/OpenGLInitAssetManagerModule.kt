package com.greentree.engine.moon.opengl.assets

import com.greentree.engine.moon.base.AssetManagerProperty
import com.greentree.engine.moon.base.property.modules.WriteProperty
import com.greentree.engine.moon.kernel.get
import com.greentree.engine.moon.modules.LaunchModule
import com.greentree.engine.moon.modules.property.EngineProperties
import com.greentree.engine.moon.opengl.assets.mesh.AttributeDataSerializator
import com.greentree.engine.moon.opengl.assets.mesh.FloatStaticDrawArrayBufferSerializator
import com.greentree.engine.moon.opengl.assets.mesh.GLVertexArraySerializator
import com.greentree.engine.moon.opengl.assets.mesh.VAORenderMeshAdapterSerializator
import com.greentree.engine.moon.opengl.assets.shader.GLSLShaderAssetSerializator
import com.greentree.engine.moon.opengl.assets.shader.GLSLShaderProgramAssetSerializator
import com.greentree.engine.moon.opengl.assets.shader.ShaderAssetSerializator
import com.greentree.engine.moon.opengl.assets.texture.GLCubeTextureAssetSerializator
import com.greentree.engine.moon.opengl.assets.texture.GLTextureAssetSerializator
import com.greentree.engine.moon.opengl.assets.texture.TextureAdapterAssetSerializator

class OpenGLInitAssetManagerModule : LaunchModule {

	@WriteProperty(AssetManagerProperty::class)
	override fun launch(context: EngineProperties) {
		val manager = context.get<AssetManagerProperty>().manager
		manager.addSerializator(GLCubeTextureAssetSerializator)
		manager.addSerializator(GLTextureAssetSerializator)
		manager.addSerializator(GLSLShaderAssetSerializator)
		manager.addSerializator(GLSLShaderProgramAssetSerializator)
		manager.addSerializator(TextureAdapterAssetSerializator)
		manager.addSerializator(VAORenderMeshAdapterSerializator)
		manager.addSerializator(AttributeDataSerializator)
		manager.addSerializator(ShaderAssetSerializator)
		manager.addSerializator(GLVertexArraySerializator)
		manager.addSerializator(FloatStaticDrawArrayBufferSerializator)
	}
}
