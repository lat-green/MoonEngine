package com.greentree.commons.assets.value.provider;

import java.util.function.Consumer;

import com.greentree.commons.assets.value.Value;

public class CecheProvider<T> implements ValueProvider<T> {
	
	public static final int CHARACTERISTICS = CECHED;
	
	private final ValueProvider<T> provider;
	
	private transient T ceche;
	
	private CecheProvider(ValueProvider<T> provider) {
		this.provider = provider;
		ceche = provider.get();
	}
	
	public static <T> ValueProvider<T> ceche(Value<T> Value) {
		final var provider = Value.openProvider();
		if(provider.hasCharacteristics(CONST) || provider.hasCharacteristics(CECHED))
			return provider;
		return new CecheProvider<>(provider);
	}
	
	@Override
	public int characteristics() {
		return provider.characteristics() | CHARACTERISTICS;
	}
	
	@Override
	public void close() {
		provider.close();
	}
	
	@Override
	public T get() {
		provider.tryGet(c-> {
			ceche = c;
		});
		return ceche;
	}
	
	
	@Override
	public T getNotChenge() {
		return ceche;
	}
	
	@Override
	public boolean isChenge() {
		return provider.isChenge();
	}
	
	@Override
	public String toString() {
		return "Ceche [" + provider + "]";
	}
	
	@Override
	public boolean tryGet(Consumer<? super T> action) {
		return provider.tryGet(c-> {
			ceche = c;
			action.accept(ceche);
		});
	}
	
}
