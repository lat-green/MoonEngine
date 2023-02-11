package com.greentree.common.assets.value;

import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;


public class CloseEventProvider<T> implements ValueProvider<T> {
	
	private final ValueProvider<T> provider;
	private final Runnable onClose;
	
	public CloseEventProvider(Value<T> value, Runnable onClose) {
		this(value.openProvider(), onClose);
	}
	
	public CloseEventProvider(ValueProvider<T> provider, Runnable onClose) {
		this.provider = provider;
		this.onClose = onClose;
	}
	
	@Override
	public int characteristics() {
		return provider.characteristics() | ~BLANCK_CLOSE;
	}
	
	@Override
	public void close() {
		onClose.run();
		provider.close();
	}
	
	@Override
	public T get() {
		return provider.get();
	}
	
	@Override
	public boolean isChenge() {
		return provider.isChenge();
	}
	
	@Override
	public ValueProvider<T> copy() {
		return new CloseEventProvider<>(provider, onClose);
	}
	
}
