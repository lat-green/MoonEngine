package com.greentree.engine.moon.opengl.assets.buffer;

import com.greentree.common.graphics.sgl.buffer.FloatStaticDrawArrayBuffer;
import com.greentree.common.graphics.sgl.vao.GLVertexArray.AttributeGroup;
import com.greentree.common.renderer.buffer.AttributeGroupData;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value2Function;
import com.greentree.commons.util.array.IntArray;
import com.greentree.engine.moon.render.assets.buffer.AttributeGroupSizesAssetKey;
import com.greentree.engine.moon.render.assets.buffer.AttributeGroupVertexAssetKey;

public class GLAttributeGroupAssetSerializator implements AssetSerializator<AttributeGroup> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(AttributeGroupData.class, key);
	}
	
	@Override
	public Value<AttributeGroup> load(LoadContext manager, AssetKey ckey) {
		if(manager.canLoad(AttributeGroupData.class, ckey)) {
			final var buffer = manager.load(FloatStaticDrawArrayBuffer.class,
					AttributeGroupVertexAssetKey.create(ckey)).toLazy();
			final var sizes = manager.load(IntArray.class, AttributeGroupSizesAssetKey.create(ckey))
					.toLazy();
			return manager.map(buffer, sizes, new AttributeGroupAsset());
		}
		return null;
	}
	
	public class AttributeGroupAsset
			implements Value2Function<FloatStaticDrawArrayBuffer, IntArray, AttributeGroup> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public AttributeGroup apply(FloatStaticDrawArrayBuffer buffer, IntArray sizes) {
			return new AttributeGroup(buffer, sizes.array);
		}
		
	}
	
}
