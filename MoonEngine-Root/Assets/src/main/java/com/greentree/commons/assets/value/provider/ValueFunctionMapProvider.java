package com.greentree.commons.assets.value.provider;

import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;


public final class ValueFunctionMapProvider<IN, OUT> extends AbstractMapProvider<IN, OUT> {
	
	public static final int CHARACTERISTICS = 0;
	private final Value1Function<? super IN, OUT> function;
	
	private ValueFunctionMapProvider(ValueProvider<IN> provider,
			Value1Function<? super IN, OUT> function) {
		super(provider);
		this.function = function;
	}
	
	public static <T, R> ValueProvider<R> newProvider(Value<T> value,
			Value1Function<? super T, R> function) {
		return newProvider(value.openProvider(), function);
	}
	
	public static <T, R> ValueProvider<R> newProvider(ValueProvider<T> provider,
			Value1Function<? super T, R> function) {
		if(provider.hasCharacteristics(CONST)) {
			final var v = provider.get();
			provider.close();
			final var r = function.apply(v);
			return ConstProvider.newValue(r);
		}
		return new ValueFunctionMapProvider<>(provider, function);
	}
	
	@Override
	public String toString() {
		return super.toString() + "[" + function.getClass().getSimpleName() + "]";
	}
	
	@Override
	protected OUT map(IN in) {
		return function.apply(in);
	}
	
}
