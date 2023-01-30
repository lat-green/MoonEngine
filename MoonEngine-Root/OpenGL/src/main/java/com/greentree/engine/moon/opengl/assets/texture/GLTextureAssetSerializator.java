package com.greentree.engine.moon.opengl.assets.texture;

import com.greentree.common.graphics.sgl.GLEnums;
import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.texture.builder.TextureBuilder;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.common.renderer.texture.Texture2DData;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;


public class GLTextureAssetSerializator implements AssetSerializator<GLTexture2DImpl> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(Texture2DData.class, key);
	}
	
	@Override
	public Value<GLTexture2DImpl> load(LoadContext manager, AssetKey ckey) {
		if(manager.canLoad(Texture2DData.class, ckey)) {
			final var texture = manager.load(Texture2DData.class, ckey);
			return manager.map(texture, new GLTextureAsset());
		}
		return null;
	}
	
	private static final class GLTextureAsset
			implements Value1Function<Texture2DData, GLTexture2DImpl> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public GLTexture2DImpl applyWithDest(Texture2DData texture, GLTexture2DImpl tex) {
			tex.close();
			return apply(texture);
		}
		
		@Override
		public GLTexture2DImpl apply(Texture2DData texture) {
			final var img = texture.image();
			final var tex = TextureBuilder
					.builder(img.getByteBuffer(), GLPixelFormat.gl(img.getFormat()))
					.build2d(img.getWidth(), img.getHeight(), GLPixelFormat.RGBA);
			
			
			tex.bind();
			final var type = texture.type();
			
			tex.setMagFilter(GLEnums.get(type.filteringMag()));
			tex.setMagFilter(GLEnums.get(type.filteringMin()));
			tex.setWrapX(GLEnums.get(type.wrapX()));
			tex.setWrapY(GLEnums.get(type.wrapY()));
			
			tex.unbind();
			
			return tex;
		}
		
	}
	
}
