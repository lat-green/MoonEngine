package com.greentree.engine.moon.assets.serializator;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.data.resource.location.ResourceLocation;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.serializator.manager.DeepValidAssetManagerBase;
import com.greentree.engine.moon.assets.value.ResourceNamedValue;
import com.greentree.engine.moon.assets.value.Value;

public class ResourceAssetSerializator implements AssetSerializator<Resource> {
	
	
	
	private final ResourceLocation resources;
	
	public ResourceAssetSerializator(ResourceLocation resources) {
		this.resources = resources;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof ResourceAssetKey k)
			return manager.canLoad(String.class, k.resourceName());
		return false;
	}
	
	@Override
	public boolean isDeepValid(DeepValidAssetManagerBase manager, AssetKey key) {
		if(key instanceof ResourceAssetKey k
				&& manager.isDeepValid(String.class, k.resourceName())) {
			final var name = manager.loadData(String.class, k.resourceName());
			return resources.getResource(name) != null;
		}
		return false;
	}
	
	@Override
	public Value<Resource> load(LoadContext context, AssetKey ckey) {
		if(ckey instanceof ResourceAssetKey key) {
			final var name = context.load(String.class, key.resourceName());
			return ResourceNamedValue.newValue(resources, name);
		}
		return null;
	}
	
	
}
