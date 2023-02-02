package com.greentree.engine.moon.opengl.assets.texture;

import com.greentree.common.graphics.sgl.texture.gl.GLTexture;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.opengl.adapter.TextureAddapter;


public class TextureAddapterAssetSerializator implements AssetSerializator<TextureAddapter> {
	
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(GLTexture.class, key);
	}
	
	@Override
	public Value<TextureAddapter> load(LoadContext manager, AssetKey ckey) {
		if(manager.canLoad(GLTexture.class, ckey)) {
			final var texture = manager.load(GLTexture.class, ckey);
			return manager.map(texture, new GLTexture_TextureAddapter_Function());
		}
		return null;
	}
	
	private static final class GLTexture_TextureAddapter_Function
			implements Value1Function<GLTexture, TextureAddapter> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public TextureAddapter apply(GLTexture texture) {
			return new TextureAddapter(texture);
		}
		
	}
	
	
	
}
