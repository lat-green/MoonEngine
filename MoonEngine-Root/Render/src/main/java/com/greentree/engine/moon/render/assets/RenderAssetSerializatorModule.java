package com.greentree.engine.moon.render.assets;


import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.annotation.WriteProperty;
import com.greentree.engine.moon.render.assets.buffer.AttributeGroupSizesAssetSerializator;
import com.greentree.engine.moon.render.assets.buffer.AttributeGroupVertexAssetSerializator;
import com.greentree.engine.moon.render.assets.buffer.FloatBufferAssetSerializotor;
import com.greentree.engine.moon.render.assets.buffer.IntBufferAssetSerializotor;
import com.greentree.engine.moon.render.assets.buffer.VertexArrayAssetSerializator;
import com.greentree.engine.moon.render.assets.buffer.VertexArrayEBOAssetSerializator;
import com.greentree.engine.moon.render.assets.buffer.VertexArrayVBOsAssetSerializator;
import com.greentree.engine.moon.render.assets.color.ColorAssetSerializer;
import com.greentree.engine.moon.render.assets.image.ImageAssetSerializator;
import com.greentree.engine.moon.render.assets.image.cube.CubeImageAssetSerializator;
import com.greentree.engine.moon.render.assets.material.MaterialDataAssetSerializator;
import com.greentree.engine.moon.render.assets.shader.ShaderDataAssetSerializator;
import com.greentree.engine.moon.render.assets.shader.ShaderProgramDataAssetSerializator;
import com.greentree.engine.moon.render.assets.texture.Texture2DAssetSerializator;
import com.greentree.engine.moon.render.assets.texture.cube.CubeTextureAssetSerializator;
import com.greentree.engine.moon.render.buffer.FloatBuffer;

public class RenderAssetSerializatorModule implements LaunchModule {
	
	private static final TypeInfo<FloatBuffer> FLOAT_BUFFER_TYPE = TypeInfoBuilder
			.getTypeInfo(FloatBuffer.class);
	
	@WriteProperty({AssetManagerProperty.class})
	@Override
	public void launch(EngineProperties context) {
		final var manager = context.get(AssetManagerProperty.class).manager();
		
		manager.addSerializator(new CubeImageAssetSerializator());
		manager.addSerializator(new CubeTextureAssetSerializator());
		
		manager.addSerializator(new ImageAssetSerializator());
		manager.addSerializator(new Texture2DAssetSerializator());
		
		manager.addSerializator(new ShaderDataAssetSerializator());
		
		manager.addSerializator(new MaterialDataAssetSerializator());
		
		manager.addSerializator(new IntBufferAssetSerializotor());
		
		manager.addSerializator(new ShaderProgramDataAssetSerializator());
		
		manager.addSerializator(new AttributeGroupVertexAssetSerializator<>(FLOAT_BUFFER_TYPE));
		manager.addSerializator(new FloatBufferAssetSerializotor());
		
		manager.addSerializator(new AttributeGroupSizesAssetSerializator());
		
		manager.addSerializator(new VertexArrayEBOAssetSerializator());
		manager.addSerializator(new VertexArrayVBOsAssetSerializator());
		manager.addSerializator(new VertexArrayAssetSerializator());
		
		manager.addSerializator(new ColorAssetSerializer());
	}
	
}
