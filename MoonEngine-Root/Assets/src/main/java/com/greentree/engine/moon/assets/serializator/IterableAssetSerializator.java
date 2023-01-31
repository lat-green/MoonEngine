package com.greentree.engine.moon.assets.serializator;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.IterableAssetKey;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.merge.MIValue;

public final class IterableAssetSerializator<T> extends AbstractIterableAssetSerializator<T> {
	
	public IterableAssetSerializator(Class<T> cls) {
		super(cls);
	}
	
	public IterableAssetSerializator(TypeInfo<T> type) {
		super(type);
	}
	
	public Value<Iterable<T>> load(LoadContext context, AssetKey ckey) {
		if(ckey instanceof IterableAssetKey key) {
			final var iter = IteratorUtil.clone(IteratorUtil.map(key, k-> {
				return context.load(ITER_TYPE, k);
			}));
			
			return new MIValue<>(iter);
		}
		return null;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof IterableAssetKey k) {
			for(var s : k)
				if(!manager.canLoad(ITER_TYPE, s))
					return false;
			return true;
		}
		return false;
	}
	
}
