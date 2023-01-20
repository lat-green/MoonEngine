package com.greentree.engine.moon.base.assets.properties;

import java.io.IOException;
import java.util.Properties;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.util.exception.WrappedException;


public class PropertiesAssetSerializator implements AssetSerializator<Properties> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(Resource.class, key);
	}
	
	@Override
	public Value<Properties> load(LoadContext context, AssetKey ckey) {
		{
			final var res = context.load(Resource.class, ckey);
			if(res != null)
				return context.map(res, PropertiesFunction.INSTANCE);
		}
		return null;
	}
	
	private static final class PropertiesFunction implements Value1Function<Resource, Properties> {
		
		private static final long serialVersionUID = 1L;
		
		private static final PropertiesFunction INSTANCE = new PropertiesFunction();
		
		@Override
		public Properties apply(Resource res) {
			if(res == null)
				return null;
			final var prop = new Properties();
			try(final var in = res.open()) {
				prop.load(in);
			}catch(IOException e) {
				throw new WrappedException(e);
			}
			return prop;
		}
		
	}
	
	
	
}
