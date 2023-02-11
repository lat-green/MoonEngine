package com.greentree.engine.moon.assets.value.merge;

import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;


public final class M2Provider<T1, T2> implements ValueProvider<Group2<T1, T2>> {
	
	public static final int CHARACTERISTICS = NOT_NULL;
	private final ValueProvider<T1> provider1;
	private final ValueProvider<T2> provider2;
	
	public M2Provider(Value<T1> value1, Value<T2> value2) {
		this(value1.openProvider(), value2.openProvider());
	}
	
	public M2Provider(ValueProvider<T1> provider1, ValueProvider<T2> provider2) {
		this.provider1 = provider1;
		this.provider2 = provider2;
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public void close() {
		provider1.close();
		provider2.close();
	}
	
	@Override
	public Group2<T1, T2> get() {
		final var v1 = provider1.get();
		final var v2 = provider2.get();
		return new Group2<>(v1, v2);
	}
	
	@Override
	public boolean isChenge() {
		return provider1.isChenge() || provider2.isChenge();
	}
	
	@Override
	public String toString() {
		return "M2Provider [" + provider1 + ", " + provider2 + "]";
	}
	
	@Override
	public ValueProvider<Group2<T1, T2>> copy() {
		return new M2Provider<>(provider1.copy(), provider2.copy());
	}
	
}
