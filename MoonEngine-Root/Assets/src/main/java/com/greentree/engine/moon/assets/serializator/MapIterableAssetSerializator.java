package com.greentree.engine.moon.assets.serializator;

import java.util.ArrayList;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.IteratableValue;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

public final class MapIterableAssetSerializator<T, R> implements AssetSerializator<Iterable<R>> {
	
	
	
	private final TypeInfo<T> T_TYPE;
	
	private final TypeInfo<R> R_TYPE;
	private final TypeInfo<Iterable<T>> T_ITER_TYPE;
	
	private final TypeInfo<Iterable<R>> R_ITER_TYPE;
	
	public MapIterableAssetSerializator(TypeInfo<T> t, TypeInfo<R> r) {
		T_TYPE = t;
		R_TYPE = r;
		T_ITER_TYPE = TypeInfoBuilder.getTypeInfo(Iterable.class, T_TYPE);
		R_ITER_TYPE = TypeInfoBuilder.getTypeInfo(Iterable.class, R_TYPE);
	}
	
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(manager.canLoad(T_ITER_TYPE, key))
			return true;
		return false;
	}
	
	@Override
	public TypeInfo<Iterable<R>> getType() {
		return R_ITER_TYPE;
	}
	
	@Override
	public Value<Iterable<R>> load(LoadContext context, AssetKey ckey) {
		if(context.canLoad(T_ITER_TYPE, ckey)) {
			final var iter = context.load(T_ITER_TYPE, ckey);
			return new IteratableValue<>(new MapIterableValue<>(context, iter, R_TYPE));
		}
		return null;
	}
	
	private static final class MapIterableValue<T, R> implements Value<Iterable<Value<R>>> {
		
		private static final long serialVersionUID = 1L;
		
		private final LoadContext context;
		
		private final Value<Iterable<T>> value;
		private final TypeInfo<R> R_TYPE;
		
		public MapIterableValue(LoadContext context, Value<Iterable<T>> value, TypeInfo<R> R_TYPE) {
			this.context = context;
			this.value = value;
			this.R_TYPE = R_TYPE;
		}
		
		@Override
		public int characteristics() {
			return Provider.CHARACTERISTICS;
		}
		
		@Override
		public ValueProvider<Iterable<Value<R>>> openProvider() {
			return new Provider(value);
		}
		
		public final class Provider implements ValueProvider<Iterable<Value<R>>> {
			
			public static final int CHARACTERISTICS = 0;
			private final ValueProvider<Iterable<T>> provider;
			
			@Override
			public ValueProvider<Iterable<Value<R>>> copy() {
				return new Provider(provider.copy());
			}
			
			public Provider(Value<Iterable<T>> value) {
				this(value.openProvider());
			}
			
			public Provider(ValueProvider<Iterable<T>> provider) {
				this.provider = provider;
			}
			
			@Override
			public int characteristics() {
				return CHARACTERISTICS;
			}
			
			@Override
			public Iterable<Value<R>> get() {
				final var iter = provider.get();
				final var result = new ArrayList<Value<R>>();
				for(var i : iter) {
					final var v = context.load(R_TYPE, i);
					result.add(v);
				}
				return result;
			}
			
			@Override
			public boolean isChenge() {
				return provider.isChenge();
			}
			
		}
		
	}
	
}
