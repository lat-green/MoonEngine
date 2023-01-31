package com.greentree.engine.moon.assets.serializator.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

import com.greentree.commons.data.resource.location.ResourceLocation;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.AssetKeyType;
import com.greentree.engine.moon.assets.location.AssetLocation;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.LoadProperty;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.AssetSerializatorContainer.AssetSerializatorInfo;
import com.greentree.engine.moon.assets.value.ConstValue;
import com.greentree.engine.moon.assets.value.DefaultValue;
import com.greentree.engine.moon.assets.value.NullValue;
import com.greentree.engine.moon.assets.value.ReduceValue;
import com.greentree.engine.moon.assets.value.Value;

public final class AssetManager implements AssetManagerBase, AsyncAssetManager,
		ValidAssetManagerBase, DeepValidAssetManagerBase {
	
	private final AssetSerializatorContainer container = new AssetSerializatorContainer();
	
	private final ExecutorService executor;
	
	
	public AssetManager() {
		executor = Executors.newFixedThreadPool(4, r-> {
			final var thread = new Thread(r, "AssetManager");
			thread.setDaemon(true);
			thread.setPriority(Thread.MAX_PRIORITY);
			return thread;
		});
	}
	
	public AssetManager(ExecutorService executor) {
		this.executor = executor;
	}
	
	public void addAssetLoction(AssetLocation location) {
		container.addAssetLoction(location);
	}
	
	public void addGenerator(
			Function<? super TypeInfo<?>, ? extends AssetSerializator<?>> generator) {
		container.addGenerator(generator);
	}
	
	public void addResourceLocation(ResourceLocation location) {
		container.addResourceLocation(location);
	}
	
	public <T> void addSerializator(AssetSerializator<T> serializator) {
		container.addSerializator(serializator);
	}
	
	@Override
	public boolean canLoad(TypeInfo<?> type, AssetKey key) {
		final var info = get(type);
		return info.canLoad(this, key);
	}
	
	public ExecutorService executor() {
		return executor;
	}
	
	@Override
	public boolean isDeepValid(TypeInfo<?> type, AssetKey key) {
		final var info = get(type);
		return info.isDeepValid(this, key);
	}
	
	@Override
	public boolean isValid(TypeInfo<?> type, AssetKey key) {
		final var info = get(type);
		return info.isValid(this, key);
	}
	
	@Override
	public <T> Value<T> load(TypeInfo<T> type, AssetKey key, T def) {
		return load(type, key, def, 0);
	}
	
	public <T> Value<T> load(TypeInfo<T> type, AssetKey key, T def, int properties) {
		final var wrapper = new LoadContextImpl(properties);
		final var result = wrapper.load(type, key, def);
		return result;
	}
	
	public <T> Value<T> load(TypeInfo<T> type, AssetKey key, T def, LoadProperty... properties) {
		final var mask = LoadProperty.getMask(properties);
		return load(type, key, def, mask);
	}
	
	@Override
	public <T> Value<T> loadAsync(TypeInfo<T> type, AssetKey key, T def) {
		return load(type, key, def, LoadProperty.LOAD_ASYNC);
	}
	
	@Override
	public <T> T loadData(TypeInfo<T> type, AssetKey key, T def) {
		return loadData(type, key, def, 0);
	}
	
	public <T> T loadData(TypeInfo<T> type, AssetKey key, T def, int properties) {
		final var wrapper = new LoadContextImpl(properties);
		final var result = wrapper.loadData(type, key, def);
		return result;
	}
	
	public <T> T loadData(TypeInfo<T> type, AssetKey key, T def, LoadProperty... properties) {
		final var mask = LoadProperty.getMask(properties);
		return loadData(type, key, def, mask);
	}
	
	@Override
	public <T> T loadDefault(TypeInfo<T> type, AssetKeyType asset_type) {
		final var info = get(type);
		return info.loadDefault(this, asset_type);
	}
	
	private <T> AssetSerializatorInfo<T> get(TypeInfo<T> type) {
		return container.get(type);
	}
	
	private final class LoadContextImpl implements LoadContext {
		
		private final LoadContextImpl parent;
		private final int properties;
		
		public LoadContextImpl(int properties) {
			this.properties = properties;
			final var parent_properties = LoadProperty.getSubAssetMask(properties);
			if(parent_properties == properties)
				parent = this;
			else
				parent = new LoadContextImpl(parent_properties);
		}
		
		@Override
		public boolean canLoad(TypeInfo<?> type, AssetKey key) {
			return AssetManager.this.canLoad(type, key);
		}
		
		public boolean has(LoadProperty property) {
			return LoadProperty.has(properties, property);
		}
		
		@Override
		public boolean isDeepValid(TypeInfo<?> type, AssetKey key) {
			return AssetManager.this.isDeepValid(type, key);
		}
		
		@Override
		public boolean isValid(TypeInfo<?> type, AssetKey key) {
			return AssetManager.this.isValid(type, key);
		}
		
		@Override
		public <T> Value<T> load(TypeInfo<T> type, AssetKey key, T def) {
			final var result = tryLoadAsync(type, key, def);
			if(def == null)
				return result;
			return DefaultValue.newValue(result, ConstValue.newValue(def));
		}
		
		@Override
		public <T> T loadData(TypeInfo<T> type, AssetKey key, T def) {
			final var info = get(type);
			try {
				final var v = info.loadData(parent, key);
				return v;
			}catch(Exception e) {
				if(def != null) {
					e.printStackTrace();
					return def;
				}
				throw e;
			}
		}
		
		@Override
		public <T> T loadDefault(TypeInfo<T> type, AssetKeyType asset_type) {
			return AssetManager.this.loadDefault(type, asset_type);
		}
		
		@Override
		public ParallelAssetManger parallel() {
			return new ParallelAssetMangerImpl();
		}
		
		@Override
		public String toString() {
			return "PropWrapAssetLoader " + Arrays.toString(LoadProperty.getArray(properties));
		}
		
		private <T> Value<T> loadForward(TypeInfo<T> type, AssetKey key, T def) {
			final var info = get(type);
			try {
				final var v = info.load(parent, key);
				return v;
			}catch(Exception e) {
				if(def != null) {
					e.printStackTrace();
					return ConstValue.newValue(def);
				}
				throw e;
			}
		}
		
		private <T> Value<T> tryLoadAsync(TypeInfo<T> type, AssetKey key, T in_def) {
			if(has(LoadProperty.LOAD_ASYNC)) {
				final var info = get(type);
				
				final T def;
				if(in_def == null)
					def = info.loadDefault(this, key.type());
				else
					def = in_def;
				
				
				if(def != null || has(LoadProperty.NULLABLE)) {
					final var async_result = new ReduceValue<>(ConstValue.newValue(def));
					executor.execute(()-> {
						try {
							final var v = loadForward(type, key, def);
							if(v == null)
								throw new RuntimeException("TYPE: " + info.TYPE);
							async_result.set(v);
						}catch(RuntimeException e) {
							e.printStackTrace();
						}
					});
					return async_result;
				}
			}
			final var result = loadForward(type, key, in_def);
			return result;
		}
		
		public final class ParallelAssetMangerImpl implements ParallelAssetManger {
			
			private final Collection<Future<?>> tasks = new ArrayList<>();
			
			
			public ParallelAssetMangerImpl() {
			}
			
			@Override
			public boolean canLoad(TypeInfo<?> type, AssetKey key) {
				return LoadContextImpl.this.canLoad(type, key);
			}
			
			@Override
			public void close() {
				for(var task : tasks)
					try {
						task.get();
					}catch(InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
			}
			
			@Override
			public <T> Value<T> load(TypeInfo<T> type, AssetKey key, T def) {
				final var result = new ReduceValue<>(NullValue.<T>instance());
				final var task = executor.submit(()-> {
					final var v = LoadContextImpl.this.load(type, key, def);
					result.set(v);
				});
				tasks.add(task);
				return result;
			}
			
			@Override
			public <T> T loadDefault(TypeInfo<T> type, AssetKeyType asset_type) {
				return LoadContextImpl.this.loadDefault(type, asset_type);
			}
			
		}
		
	}
	
}
