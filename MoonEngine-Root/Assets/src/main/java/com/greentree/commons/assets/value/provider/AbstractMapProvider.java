package com.greentree.commons.assets.value.provider;

import java.util.function.Consumer;

import com.greentree.commons.assets.value.Value;

public abstract class AbstractMapProvider<IN, OUT> implements ValueProvider<OUT> {
	
	private static final int CHARACTERISTICS = 0;
	protected final ValueProvider<IN> input;
	
	public AbstractMapProvider(Value<IN> input) {
		this(input.openProvider());
	}
	
	public AbstractMapProvider(ValueProvider<IN> input) {
		this.input = input;
	}
	
	@Override
	public final int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public void close() {
		input.close();
	}
	
	@Override
	public String toString() {
		return "AbstractMapProvider [" + input + "]";
	}
	
	@Override
	public boolean tryGet(Consumer<? super OUT> action) {
		return input.tryGet(v-> {
			action.accept(map(v));
		});
	}
	
	@Override
	public boolean isChenge() {
		return input.isChenge();
	}
	
	@Override
	public OUT get() {
		final var value = input.get();
		return map(value);
	}
	
	protected abstract OUT map(IN in);
	
}
