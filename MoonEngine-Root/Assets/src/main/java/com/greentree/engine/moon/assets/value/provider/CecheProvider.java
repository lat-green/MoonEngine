package com.greentree.engine.moon.assets.value.provider;

import java.util.function.Consumer;

import com.greentree.engine.moon.assets.value.Value;

public class CecheProvider<T> implements ValueProvider<T> {
	
	public static final int CHARACTERISTICS = CECHED;
	
	private final ValueProvider<T> provider;
	
	private transient T ceche;
	
	private CecheProvider(ValueProvider<T> provider) {
		this.provider = provider;
		ceche = provider.get();
	}
	
	@Override
	public ValueProvider<T> copy() {
		return ceche(provider.copy());
	}
	
	public static <T> ValueProvider<T> ceche(ValueProvider<T> provider) {
		if(provider.hasCharacteristics(CONST) || provider.hasCharacteristics(CECHED))
			return provider;
		return new CecheProvider<>(provider);
	}
	
	public static <T> ValueProvider<T> ceche(Value<T> Value) {
		final var provider = Value.openProvider();
		return ceche(provider);
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
