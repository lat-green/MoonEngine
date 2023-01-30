package com.greentree.commons.assets.value.provider;

public final class ConstProvider<T> implements ValueProvider<T> {
	
	public static final int CHARACTERISTICS = CONST | NOT_NULL | BLANCK_CLOSE | DISTINCT_CHANGE
			| CECHED;
	
	private final T value;
	
	private ConstProvider(T value) {
		this.value = value;
	}
	
	
	public static <T> ValueProvider<T> newValue(T value) {
		if(value == null)
			return NullProvider.instance();
		return new ConstProvider<>(value);
	}
	
	@Override
	public String toString() {
		return "ConstProvider [" + value + "]";
	}
	
	@Override
	public void close() {
	}
	
	@Override
	public boolean isChenge() {
		return false;
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public T get() {
		return value;
	}
	
}
