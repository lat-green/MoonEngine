package com.greentree.engine.moon.base.assets.text;

import java.io.IOException;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.util.InputStreamUtil;
import com.greentree.commons.util.exception.WrappedException;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.serializator.manager.DeepValidAssetManagerBase;
import com.greentree.engine.moon.assets.serializator.manager.ValidAssetManagerBase;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;

public class StringAssetSerializor implements AssetSerializator<String> {
	
	@Override
	public Value<String> load(LoadContext manager, AssetKey ckey) {
		if(manager.canLoad(Resource.class, ckey)) {
			final var res = manager.load(Resource.class, ckey);
			return manager.map(res, new StringAsset());
		}
		return null;
	}
	
	@Override
	public boolean isDeepValid(DeepValidAssetManagerBase manager, AssetKey key) {
		if(manager.isDeepValid(Resource.class, key)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isValid(ValidAssetManagerBase manager, AssetKey key) {
		if(manager.isValid(Resource.class, key))
			return true;
		return false;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(Resource.class, key);
	}
	
	private static final class StringAsset implements Value1Function<Resource, String> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public String apply(Resource res) {
			if(res == null)
				return null;
			try(final var in = res.open()) {
				return InputStreamUtil.readString(in);
			}catch(IOException e) {
				throw new WrappedException(e);
			}
		}
		
	}
	
}
