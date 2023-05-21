package com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.assets.value.provider.AbstractMapProvider;
import com.greentree.engine.moon.assets.value.provider.ConstProvider;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

public final class ValueFunctionMapValue<T, R> extends AbstractRefCountValue<R> {
	
	private static final long serialVersionUID = 1L;
	private final Value<T> value;
	private final Value1Function<? super T, R> function;
	
	private ValueFunctionMapValue(Value<T> value, Value1Function<? super T, R> function) {
		this.value = value;
		this.function = function;
	}
	
	public static <T, R> Value<R> newValue(Value<T> value, Value1Function<? super T, R> function) {
		if(value.hasCharacteristics(CONST)) {
			final var p = value.openProvider();
				final var v = p.get();
				final var r = function.apply(v);
				return ConstValue.newValue(r);
		}
		return new ValueFunctionMapValue<>(value, function);
	}
	
	@Override
	public String toString() {
		return "ValueFunctionMapValue [" + value + ", " + function.getClass().getSimpleName() + "]";
	}
	
	@Override
	protected ValueProvider<R> openRawProvider() {
		return Provider.newProvider(value, function);
	}
	
	private static final class Provider<IN, OUT> extends AbstractMapProvider<IN, OUT> {
		
		private final Value1Function<? super IN, OUT> function;
		
		private Provider(ValueProvider<IN> provider, Value1Function<? super IN, OUT> function) {
			super(provider);
			this.function = function;
		}
		
		public static <T, R> ValueProvider<R> newProvider(Value<T> value, Value1Function<? super T, R> function) {
			return newProvider(value.openProvider(), function);
		}
		
		public static <T, R> ValueProvider<R> newProvider(ValueProvider<T> provider,
				Value1Function<? super T, R> function) {
			if(provider.hasCharacteristics(CONST)) {
				final var v = provider.get();
				final var r = function.apply(v);
				return ConstProvider.newValue(r);
			}
			return new Provider<>(provider, function);
		}
		
		@Override
		public String toString() {
			return super.toString() + "[" + function.getClass().getSimpleName() + "]";
		}
		
		@Override
		protected OUT map(IN in) {
			return function.apply(in);
		}
		
		@Override
		public ValueProvider<OUT> copy() {
			return newProvider(input.copy(), function);
		}
		
	}
	
}
