package com.greentree.engine.moon.render.assets.buffer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.commons.util.classes.info.TypeUtil;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value2Function;
import com.greentree.engine.moon.render.buffer.AttributeGroupData;
import com.greentree.engine.moon.render.buffer.VertexArrayData;

public class VertexArrayDataAssetSerializator<EBO, ATTR>
		implements AssetSerializator<VertexArrayData<EBO, ATTR>> {
	
	private static final TypeInfo<VertexArrayData<IntBuffer, AttributeGroupData<FloatBuffer>>> TYPE = TypeInfoBuilder
			.getTypeInfo(VertexArrayData.class, IntBuffer.class,
					TypeInfoBuilder.getTypeInfo(AttributeGroupData.class, FloatBuffer.class));
	
	
	private final TypeInfo<ATTR> ATTR_TYPE;
	private final TypeInfo<EBO> EBO_TYPE;
	private final TypeInfo<Iterable<ATTR>> ATTR_ITER_TYPE;
	
	@SuppressWarnings("unchecked")
	public VertexArrayDataAssetSerializator() {
		final var type = TypeUtil.getTtype(TypeInfoBuilder.getTypeInfo(getClass()),
				VertexArrayDataAssetSerializator.class).getTypeArguments();
		ATTR_TYPE = (TypeInfo<ATTR>) type[0];
		EBO_TYPE = (TypeInfo<EBO>) type[1];
		ATTR_ITER_TYPE = TypeInfoBuilder.getTypeInfo(Iterable.class, ATTR_TYPE);
	}
	
	public VertexArrayDataAssetSerializator(TypeInfo<EBO> ebo_type, TypeInfo<ATTR> attr_type) {
		ATTR_TYPE = attr_type;
		EBO_TYPE = ebo_type;
		ATTR_ITER_TYPE = TypeInfoBuilder.getTypeInfo(Iterable.class, ATTR_TYPE);
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof VAOAssetKey k)
			return manager.canLoad(EBO_TYPE, k.ebo()) && manager.canLoad(ATTR_ITER_TYPE, k.vbos());
		if(manager.canLoad(TYPE, key))
			return true;
		return false;
	}
	
	@Override
	public TypeInfo<VertexArrayData<EBO, ATTR>> getType() {
		return TypeInfoBuilder.getTypeInfo(VertexArrayData.class, EBO_TYPE, ATTR_TYPE);
	}
	
	@Override
	public Value<VertexArrayData<EBO, ATTR>> load(LoadContext manager, AssetKey ckey) {
		if(ckey instanceof VAOAssetKey key) {
			final var ebo = manager.load(EBO_TYPE, key.ebo());
			final var vbos = manager.load(ATTR_ITER_TYPE, key.vbos());
			return manager.map(ebo, vbos, new VAOAsset<>());
		}
		if(manager.canLoad(TYPE, ckey)) {
			final var eboKey = new VertexArrayEBOAssetKey(ckey);
			final var vbosKey = new VertexArrayVBOsAssetKey(ckey);
			final var ebo = manager.load(EBO_TYPE, eboKey);
			final var vbos = manager.load(ATTR_ITER_TYPE, vbosKey);
			return manager.map(ebo, vbos, new VAOAsset<>());
		}
		return null;
	}
	
	private static final class VAOAsset<EBO, ATTR>
			implements Value2Function<EBO, Iterable<ATTR>, VertexArrayData<EBO, ATTR>> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public VertexArrayData<EBO, ATTR> apply(EBO t1, Iterable<ATTR> t2) {
			return new VertexArrayData<>(t1, t2);
		}
		
	}
	
}
