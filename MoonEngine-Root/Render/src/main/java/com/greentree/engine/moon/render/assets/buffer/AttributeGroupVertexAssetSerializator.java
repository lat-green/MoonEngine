package com.greentree.engine.moon.render.assets.buffer;

import com.greentree.common.renderer.buffer.AttributeGroupData;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;

public final class AttributeGroupVertexAssetSerializator<VBO> implements AssetSerializator<VBO> {
	
	private final TypeInfo<VBO> TYPE;
	private final TypeInfo<AttributeGroupData<VBO>> ATTR_TYPE;
	
	public AttributeGroupVertexAssetSerializator(TypeInfo<VBO> VBO_TYPE) {
		TYPE = VBO_TYPE;
		ATTR_TYPE = TypeInfoBuilder.getTypeInfo(AttributeGroupData.class, TYPE);
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof AttributeGroupVertexAssetKey k)
			return true;
		return false;
	}
	
	@Override
	public TypeInfo<VBO> getType() {
		return TYPE;
	}
	
	@Override
	public Value<VBO> load(LoadContext context, AssetKey ckey) {
		if(ckey instanceof AttributeGroupVertexAssetKey key) {
			final var array = context.load(ATTR_TYPE, key.group());
			return context.map(array, VertexAsset.getInstance());
		}
		return null;
	}
	
	private static final class VertexAsset<VBO>
			implements Value1Function<AttributeGroupData<VBO>, VBO> {
		
		private static final long serialVersionUID = 1L;
		
		private static final VertexAsset<?> INSTANCE = new VertexAsset<>();
		
		@SuppressWarnings("unchecked")
		public static <VBO> VertexAsset<VBO> getInstance() {
			return (VertexAsset<VBO>) INSTANCE;
		}
		
		@Override
		public VBO apply(AttributeGroupData<VBO> arr) {
			return arr.vertex();
		}
		
	}
	
}
