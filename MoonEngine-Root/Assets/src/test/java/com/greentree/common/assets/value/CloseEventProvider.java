package com.greentree.common.assets.value;

import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;


public class CloseEventProvider<T> implements ValueProvider<T> {
	
	private final ValueProvider<T> provider;
	private final Runnable onClose;
	
	public CloseEventProvider(Value<T> Value, Runnable onClose) {
		this.provider = Value.openProvider();
		this.onClose = onClose;
	}
	
	@Override
	public int characteristics() {
		return provider.characteristics() | ~BLANCK_CLOSE;
	}
	
	@Override
	public T get() {
		return provider.get();
	}
	
	@Override
	public void close() {
		onClose.run();
		provider.close();
	}
	
	@Override
	public boolean isChenge() {
		return provider.isChenge();
	}
	
}
