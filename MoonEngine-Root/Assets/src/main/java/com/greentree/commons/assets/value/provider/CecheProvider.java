package com.greentree.commons.assets.value.provider;

import java.util.function.Consumer;

import com.greentree.commons.assets.value.Value;

public class CecheProvider<T> implements ValueProvider<T> {
	
	public static final int CHARACTERISTICS = CECHED;
	
	private final ValueProvider<T> provider;
	
	private transient T ceche;
	
	@Override
	public String toString() {
		return "Ceche [" + provider + ", " + ceche + "]";
	}
	
	public static <T> ValueProvider<T> ceche(Value<T> Value) {
		final var provider = Value.openProvider();
		if(provider.hasCharacteristicConst())
			return provider;
		return new CecheProvider<>(provider);
	}
	
	@Override
	public void close() {
		provider.close();
	}
	
	private CecheProvider(ValueProvider<T> provider) {
		this.provider = provider;
		ceche = provider.get();
	}
	
	@Override
	public int characteristics() {
		return provider.characteristics() | CHARACTERISTICS;
	}
	
	
	@Override
	public T getNotChenge() {
		return ceche;
	}
	
	@Override
	public boolean tryGet(Consumer<? super T> action) {
		return provider.tryGet(c-> {
			ceche = c;
			action.accept(ceche);
		});
	}
	
	@Override
	public T get() {
		provider.tryGet(c-> {
			ceche = c;
		});
		return ceche;
	}
	
	@Override
	public boolean isChenge() {
		return provider.isChenge();
	}
	
}
