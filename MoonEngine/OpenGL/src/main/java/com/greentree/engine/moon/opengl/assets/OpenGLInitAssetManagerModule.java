package com.greentree.engine.moon.opengl.assets;

import com.greentree.common.graphics.sgl.vao.GLVertexArray.AttributeGroup;
import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.opengl.assets.mesh.AttributeDataSerializator;
import com.greentree.engine.moon.opengl.assets.mesh.FloatStaticDrawArrayBufferSerializator;
import com.greentree.engine.moon.opengl.assets.mesh.GLVertexArraySerializator;
import com.greentree.engine.moon.opengl.assets.mesh.VAORenderMeshAdapterSerializator;
import com.greentree.engine.moon.opengl.assets.shader.GLSLShaderAssetSerializator;
import com.greentree.engine.moon.opengl.assets.shader.GLSLShaderProgramAssetSerializator;
import com.greentree.engine.moon.opengl.assets.shader.ShaderAssetSerializator;
import com.greentree.engine.moon.opengl.assets.texture.GLCubeTextureAssetSerializator;
import com.greentree.engine.moon.opengl.assets.texture.GLTextureAssetSerializator;
import com.greentree.engine.moon.opengl.assets.texture.TextureAdapterAssetSerializator;

public final class OpenGLInitAssetManagerModule implements LaunchModule {

    private static final TypeInfo<AttributeGroup> ATTRIBUTE_GROUP_TYPE = TypeInfoBuilder
            .getTypeInfo(AttributeGroup.class);

    @WriteProperty({AssetManagerProperty.class})
    @Override
    public void launch(EngineProperties context) {
        final var manager = context.get(AssetManagerProperty.class).manager;
        manager.addSerializator(GLCubeTextureAssetSerializator.INSTANCE);
        manager.addSerializator(GLTextureAssetSerializator.INSTANCE);
        manager.addSerializator(GLSLShaderAssetSerializator.INSTANCE);
        manager.addSerializator(GLSLShaderProgramAssetSerializator.INSTANCE);
        manager.addSerializator(TextureAdapterAssetSerializator.INSTANCE);
        manager.addSerializator(VAORenderMeshAdapterSerializator.INSTANCE);
        manager.addSerializator(AttributeDataSerializator.INSTANCE);
        manager.addSerializator(ShaderAssetSerializator.INSTANCE);
        manager.addSerializator(GLVertexArraySerializator.INSTANCE);
        manager.addSerializator(FloatStaticDrawArrayBufferSerializator.INSTANCE);
    }

}
