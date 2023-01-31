package com.greentree.engine.moon.assets.serializator.request.builder;

import java.util.function.Function;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.request.KeyLoadRequest;
import com.greentree.engine.moon.assets.serializator.request.KeyLoadRequestImpl;

public final class KeyRequestBuilderImpl<T, R> implements KeyRequestBuilder<T, R> {
	
	private final Function<? super KeyLoadRequest<T>, ? extends R> manager;
	
	private T def;
	
	private AssetKey key;
	private final TypeInfo<T> type;
	
	public KeyRequestBuilderImpl(Function<? super KeyLoadRequest<T>, ? extends R> manager,
			TypeInfo<T> type) {
		this.manager = manager;
		this.type = type;
	}
	
	@Override
	public R load() {
		return manager.apply(new KeyLoadRequestImpl<T>(type, key, def));
	}
	
	@Override
	public KeyRequestBuilder<T, R> set(AssetKey key) {
		this.key = key;
		return this;
	}
	
	@Override
	public KeyRequestBuilder<T, R> setDefault(T def) {
		this.def = def;
		return this;
	}
	
}
