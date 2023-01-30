package com.greentree.commons.assets.serializator.manager;

import java.io.ObjectStreamException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.AssetKeyType;
import com.greentree.commons.assets.location.AssetLocation;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.DefaultSerializator;
import com.greentree.commons.assets.serializator.MultiAssetSerializator;
import com.greentree.commons.assets.serializator.NamedAssetSerializator;
import com.greentree.commons.assets.serializator.NullSerializator;
import com.greentree.commons.assets.serializator.ResourceAssetSerializator;
import com.greentree.commons.assets.serializator.ResultSerializator;
import com.greentree.commons.assets.serializator.TypedAssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.value.AbstractRefCountValue;
import com.greentree.commons.assets.value.CecheValue;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.provider.ProxyProvider;
import com.greentree.commons.assets.value.provider.ValueProvider;
import com.greentree.commons.data.resource.location.ResourceLocation;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeUtil;
import com.greentree.commons.util.iterator.IteratorUtil;

final class AssetSerializatorContainer {
	
	
	
	private final Map<TypeInfo<?>, AssetSerializatorInfo<?>> serializators = new HashMap<>();
	
	private final Collection<Function<? super TypeInfo<?>, ? extends AssetSerializator<?>>> generators = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public AssetSerializatorContainer() {
		addGenerator(DefaultSerializator::new);
		addGenerator(ResultSerializator::new);
		addGenerator(NullSerializator::new);
	}
	
	public void addAssetLoction(AssetLocation location) {
		addGenerator(t->new NamedAssetSerializator<>(location, t));
	}
	
	public void addGenerator(
			Function<? super TypeInfo<?>, ? extends AssetSerializator<?>> generator) {
		generators.add(generator);
		synchronized(serializators) {
			for(var t : serializators.keySet())
				runGenerator(t, generator);
		}
	}
	
	public void addResourceLocation(ResourceLocation location) {
		addSerializator(new ResourceAssetSerializator(location));
	}
	
	public <T> void addSerializator(AssetSerializator<T> serializator) {
		final var type = serializator.getType();
		final var info = get(type);
		info.addSerializator(serializator);
	}
	
	@SuppressWarnings("unchecked")
	public <T> AssetSerializatorInfo<T> get(TypeInfo<T> type) {
		synchronized(serializators) {
			if(!serializators.containsKey(type)) {
				final var info = new AssetSerializatorInfo<>(type);
				for(var s : serializators.keySet()) {
					if(TypeUtil.isExtends(s, type)) {
						final var ss = (AssetSerializatorInfo<T>) serializators.get(s);
						ss.serializatorInfos.add(info);
					}
					if(TypeUtil.isExtends(type, s)) {
						final var ss = (AssetSerializatorInfo<T>) serializators.get(s);
						info.serializatorInfos.add(ss);
					}
				}
				serializators.put(type, info);
				onNewType(type);
				return info;
			}
			return (AssetSerializatorInfo<T>) serializators.get(type);
		}
	}
	
	private void onNewType(TypeInfo<?> type) {
		for(var g : generators)
			runGenerator(type, g);
	}
	
	private void runGenerator(TypeInfo<?> type,
			Function<? super TypeInfo<?>, ? extends AssetSerializator<?>> generator) {
		final var s = generator.apply(type);
		if(s != null)
			addSerializator(s);
	}
	
	public final class AssetSerializatorInfo<T> extends TypedAssetSerializator<T> {
		
		private final List<AssetSerializatorInfo<T>> serializatorInfos = new ArrayList<>();
		private final List<AssetSerializator<T>> serializators = new ArrayList<>();
		private final AssetSerializator<T> serializator = new MultiAssetSerializator<>(
				IteratorUtil.union(serializators, serializatorInfos));
		
		private final Ceche<AssetKey, Value<T>> cache = new Ceche<>();
		
		public AssetSerializatorInfo(TypeInfo<T> type) {
			super(type);
		}
		
		public void addSerializator(AssetSerializator<T> serializator) {
			serializators.add(serializator);
		}
		
		@Override
		public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
			if(cache.has(key))
				return true;
			return serializator.canLoad(manager, key);
		}
		
		@Override
		public boolean isDeepValid(DeepValidAssetManagerBase manager, AssetKey key) {
			final var value = cache.get(key);
			if(value != null)
				return !value.isNull();
			return serializator.isDeepValid(manager, key);
		}
		
		@Override
		public boolean isValid(ValidAssetManagerBase manager, AssetKey key) {
			final var value = cache.get(key);
			if(value != null)
				return !value.isNull();
			return serializator.isValid(manager, key);
		}
		
		@Override
		public Value<T> load(LoadContext context, AssetKey key) {
			final var v = cache.set(key, close-> {
				try {
					var value = serializator.load(context, key);
					value = new CechedValue<>(value, close);
					value = CecheValue.ceche(value);
					return value;
				}catch(Exception e) {
					throw new IllegalArgumentException("type:" + TYPE + " key:" + key, e);
				}
			});
			return v;
		}
		
		public T loadData(LoadContext context, AssetKey key) {
			final var value = serializator.load(context, key);
			try(final var p = value.openProvider()) {
				return p.get();
			}
		}
		
		@Override
		public T loadDefault(DefaultAssetManager manager, AssetKeyType asset_type) {
			return serializator.loadDefault(manager, asset_type);
		}
		
		@Override
		public String toString() {
			return "AssetSerializatorInfo [" + TYPE + "]";
		}
		
	}
	
	private static final class CechedValue<T> extends AbstractRefCountValue<T> {
		
		private static final long serialVersionUID = 1L;
		
		@Deprecated
		@Override
		public T get() {
			return value.get();
		}
		
		@Deprecated
		@Override
		public boolean isNull() {
			if(!hasCharacteristics(NOT_NULL))
				return false;
			return value.isNull();
		}
		
		private final Value<T> value;
		
		private final Runnable onClose;
		
		public CechedValue(Value<T> Value, Runnable close) {
			this.value = Value;
			this.onClose = close;
		}
		
		@Override
		protected void clear() {
			onClose.run();
		}
		
		@Override
		protected int rawCharacteristics() {
			return value.characteristics();
		}
		
		@Override
		public ValueProvider<T> openRawProvider() {
			return value.openProvider();
		}
		
		@Override
		public String toString() {
			return "CechedValue [" + value + "]";
		}
		
		@Serial
		private Object writeReplace() throws ObjectStreamException {
			return value;
		}
		
	}
}
