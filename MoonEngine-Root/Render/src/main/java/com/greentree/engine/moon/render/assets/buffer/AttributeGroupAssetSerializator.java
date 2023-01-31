package com.greentree.engine.moon.render.assets.buffer;

import com.greentree.commons.util.array.IntArray;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value2Function;
import com.greentree.engine.moon.render.buffer.AttributeGroupData;


public final class AttributeGroupAssetSerializator<VBO>
		implements AssetSerializator<AttributeGroupData<VBO>> {
	
	public final TypeInfo<VBO> VBO_TYPE;
	
	public AttributeGroupAssetSerializator(TypeInfo<VBO> type) {
		VBO_TYPE = type;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return key instanceof AttributeGroupAssetKey;
	}
	
	@Override
	public Value<AttributeGroupData<VBO>> load(LoadContext context, AssetKey ckey) {
		if(ckey instanceof AttributeGroupAssetKey key) {
			final var vbo = context.load(VBO_TYPE, key.vbo());
			final var sizes = context.load(IntArray.class, key.sizes());
			return context.map(vbo, sizes, AttributeGroupAsset.getInstance());
		}
		return null;
	}
	
	private static final class AttributeGroupAsset<VBO>
			implements Value2Function<VBO, IntArray, AttributeGroupData<VBO>> {
		
		private static final long serialVersionUID = 1L;
		
		private static final AttributeGroupAsset<?> INSTANCE = new AttributeGroupAsset<>();
		
		@SuppressWarnings("unchecked")
		public static <T> AttributeGroupAsset<T> getInstance() {
			return (AttributeGroupAsset<T>) INSTANCE;
		}
		
		@Override
		public AttributeGroupData<VBO> apply(VBO value1, IntArray value2) {
			return new AttributeGroupData<>(value1, value2);
		}
		
	}
	
}
