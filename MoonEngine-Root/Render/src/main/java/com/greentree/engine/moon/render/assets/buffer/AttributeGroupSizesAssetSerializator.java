package com.greentree.engine.moon.render.assets.buffer;

import com.greentree.common.renderer.buffer.AttributeGroupData;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.util.array.IntArray;

public final class AttributeGroupSizesAssetSerializator implements AssetSerializator<IntArray> {
	
	@SuppressWarnings("rawtypes")
	private static final class SizesFunction
			implements Value1Function<AttributeGroupData, IntArray> {
		
		private static final long serialVersionUID = 1L;
		public static final SizesFunction INSTANCE = new SizesFunction();
		
		@Override
		public IntArray apply(AttributeGroupData value) {
			return value.sizes();
		}
		
	}
	
	@Override
	public Value<IntArray> load(LoadContext context, AssetKey ckey) {
		if(ckey instanceof AttributeGroupSizesAssetKey key) {
			final var array = context.load(AttributeGroupData.class, key.group());
			return context.map(array, SizesFunction.INSTANCE);
		}
		return null;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof AttributeGroupSizesAssetKey k)
			return true;
		return false;
	}
	
}
