package com.greentree.common.assets.value;

import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.provider.ValueProvider;


public class CloseEventValue<T> implements Value<T> {
	
	private static final long serialVersionUID = 1L;
	private final Runnable onClose;
	private final Value<T> Value;
	
	public CloseEventValue(Value<T> Value, Runnable onClose) {
		this.Value = Value;
		this.onClose = onClose;
	}
	
	@Override
	public int characteristics() {
		return 0;
	}
	
	@Override
	public ValueProvider<T> openProvider() {
		return new CloseEventProvider<>(Value, onClose);
	}
	
}
