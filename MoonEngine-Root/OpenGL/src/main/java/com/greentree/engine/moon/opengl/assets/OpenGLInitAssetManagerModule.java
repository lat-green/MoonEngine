package com.greentree.engine.moon.opengl.assets;

import com.greentree.common.graphics.sgl.buffer.FloatStaticDrawArrayBuffer;
import com.greentree.common.graphics.sgl.buffer.IntStaticDrawElementArrayBuffer;
import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.common.graphics.sgl.vao.GLVertexArray.AttributeGroup;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.serializator.IterableAssetSerializator;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.annotation.WriteProperty;
import com.greentree.engine.moon.opengl.assets.material.GLPBRMaterialAssetSerializator;
import com.greentree.engine.moon.opengl.assets.material.MaterialAssetSerializator;
import com.greentree.engine.moon.opengl.assets.shader.GLSLShaderAssetSerializator;
import com.greentree.engine.moon.opengl.assets.shader.GLSLShaderProgramAssetSerializator;
import com.greentree.engine.moon.opengl.assets.texture.GLCubeTextureAssetSerializator;
import com.greentree.engine.moon.opengl.assets.texture.GLTextureAssetSerializator;
import com.greentree.engine.moon.opengl.assets.texture.TextureAddapterAssetSerializator;
import com.greentree.engine.moon.render.assets.material.PBRMaterialAssetSerializator;


public final class OpenGLInitAssetManagerModule implements LaunchModule {
	private static final TypeInfo<FloatStaticDrawArrayBuffer> VBO_TYPE = TypeInfoBuilder
			.getTypeInfo(FloatStaticDrawArrayBuffer.class);
	private static final TypeInfo<IntStaticDrawElementArrayBuffer> EBO_TYPE = TypeInfoBuilder
			.getTypeInfo(IntStaticDrawElementArrayBuffer.class);
	private static final TypeInfo<GLSLShader> SHADER_TYPE = TypeInfoBuilder.getTypeInfo(GLSLShader.class);
	private static final TypeInfo<GLTexture2DImpl> TEXTURE_TYPE = TypeInfoBuilder.getTypeInfo(GLTexture2DImpl.class);
	
	private static final TypeInfo<AttributeGroup> ATTRIBUTE_GROUP_TYPE = TypeInfoBuilder
			.getTypeInfo(AttributeGroup.class);
	
	@WriteProperty({AssetManagerProperty.class})
	@Override
	public void launch(EngineProperties context) {
		final var manager = context.get(AssetManagerProperty.class).manager();
		
		manager.addSerializator(new IterableAssetSerializator<>(ATTRIBUTE_GROUP_TYPE));
		
		manager.addSerializator(new GLCubeTextureAssetSerializator());
		
		manager.addSerializator(new GLTextureAssetSerializator());
		
		manager.addSerializator(new GLSLShaderAssetSerializator());
		manager.addSerializator(new GLSLShaderProgramAssetSerializator());
		
		manager.addSerializator(new PBRMaterialAssetSerializator<>(TEXTURE_TYPE));
		manager.addSerializator(new GLPBRMaterialAssetSerializator());
		
		manager.addSerializator(new MaterialAssetSerializator());
		
		manager.addSerializator(new TextureAddapterAssetSerializator());
	}
	
}
