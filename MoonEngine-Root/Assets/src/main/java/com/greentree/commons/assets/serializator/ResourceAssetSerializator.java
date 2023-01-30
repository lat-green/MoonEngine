package com.greentree.commons.assets.serializator;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResourceAssetKey;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.DeepValidAssetManagerBase;
import com.greentree.commons.assets.value.ResourceValue;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.provider.ReduceProvider;
import com.greentree.commons.assets.value.provider.ValueProvider;
import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.data.resource.location.ResourceLocation;

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
			return new ResourceAsset(resources, name);
		}
		return null;
	}
	
	private static final class ResourceAsset implements Value<Resource> {
		
		private static final long serialVersionUID = 1L;
		private final ResourceLocation resources;
		private final Value<? extends String> name;
		
		public ResourceAsset(ResourceLocation resources, Value<? extends String> name) {
			this.name = name;
			this.resources = resources;
		}
		
		@Override
		public int characteristics() {
			return ResourceAssetProvider.CHARACTERISTICS;
		}
		
		@Override
		public ValueProvider<Resource> openProvider() {
			return new ReduceProvider<Resource>(new ResourceAssetProvider(resources, name));
		}
		
		public class ResourceAssetProvider implements ValueProvider<ResourceValue> {
			
			
			@Override
			public String toString() {
				return "ResourceAssetProvider [" + name + "]";
			}
			
			private static final int CHARACTERISTICS = 0;
			private final ResourceLocation resources;
			private final ValueProvider<? extends String> name;
			
			public ResourceAssetProvider(ResourceLocation resources, Value<? extends String> name) {
				this.name = name.openProvider();
				this.resources = resources;
			}
			
			@Override
			public int characteristics() {
				return CHARACTERISTICS;
			}
			
			@Override
			public void close() {
			}
			
			@Override
			public ResourceValue get() {
				final var name = this.name.get();
				if(name == null)
					return null;
				final var resource = resources.getResource(name);
				if(resource == null)
					throw new IllegalArgumentException("reValue not found " + name);
				return new ResourceValue(resource);
			}
			
			@Override
			public boolean isChenge() {
				return name.isChenge();
			}
			
		}
		
	}
	
	
}
