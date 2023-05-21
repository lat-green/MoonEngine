package com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.provider.ValueProvider;

public final class MutableValue<T> implements Value<T> {
	
	
	private static final long serialVersionUID = 1L;
	
	private T value;
	private long lastSet = Long.MIN_VALUE;
	
	public MutableValue() {
	}
	
	public MutableValue(T value) {
		set(value);
	}
	
	@Override
	public int characteristics() {
		return MutableProvider.CHARACTERISTICS;
	}
	
	@Override
	public ValueProvider<T> openProvider() {
		return new MutableProvider();
	}
	
	public void set(T value) {
		lastSet++;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Mutable [" + value + "]";
	}
	
	private final class MutableProvider implements ValueProvider<T> {
		
		public static final int CHARACTERISTICS = BLANCK_CLOSE;
		
		private long lastGet = lastSet;
		
		@Override
		public int characteristics() {
			return CHARACTERISTICS;
		}
		
		@Override
		public T get() {
			lastGet = lastSet;
			return value;
		}
		
		@Override
		public boolean isChenge() {
			return lastSet > lastGet;
		}
		
		@Override
		public String toString() {
			return "MutableProvider [" + value + "]";
		}
		
		@Override
		public ValueProvider<T> copy() {
			return new MutableProvider();
		}
		
	}
	
}
