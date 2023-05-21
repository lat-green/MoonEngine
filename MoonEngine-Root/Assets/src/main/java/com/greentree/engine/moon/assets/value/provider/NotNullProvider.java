package com.greentree.engine.moon.assets.value.provider;

import com.greentree.engine.moon.assets.value.Value;

public final class NotNullProvider<T> implements ValueProvider<T> {
	
	public static final int CHARACTERISTICS = NOT_NULL | CECHED;
	private final ValueProvider<T> provider;
	
	private T ceche;
	
	private NotNullProvider(ValueProvider<T> provider) {
		this.provider = provider;
		ceche = provider.get();
		if(ceche == null)
			throw new NullPointerException("init NotNullProvider null. provider: " + provider);
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS | provider.characteristics();
	}
	
	@Override
	public T getNotChenge() {
		return ceche;
	}
	
	@Override
	public T get() {
		return ceche;
	}
	
	@Override
	public boolean isChenge() {
		return provider.tryGet(c-> {
			if(c != null)
				ceche = c;
		});
	}
	
	@Override
	public String toString() {
		return "NotNullProvider [" + provider + "]";
	}
	
	public static <T> ValueProvider<T> newProvider(Value<T> value) {
		return newProvider(value.openProvider());
	}
	
	public static <T> ValueProvider<T> newProvider(ValueProvider<T> provider) {
		if(provider.hasCharacteristics(NOT_NULL))
			return provider;
		if(provider.hasCharacteristics(CONST)) {
			final var v = provider.get();
			if(v == null)
				throw new NullPointerException("const null provider:" + provider);
			return ConstProvider.newValue(v);
		}
		return new NotNullProvider<>(provider);
	}
	
	@Override
	public ValueProvider<T> copy() {
		return newProvider(provider.copy());
	}
	
}
