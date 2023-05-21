package com.greentree.engine.moon.assets.value.merge;

import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;


public final class M3Provider<T1, T2, T3> implements ValueProvider<Group3<T1, T2, T3>> {
	
	public static final int CHARACTERISTICS = NOT_NULL;
	private final ValueProvider<T1> provider1;
	private final ValueProvider<T2> provider2;
	private final ValueProvider<T3> provider3;
	
	public M3Provider(Value<T1> value1, Value<T2> value2, Value<T3> value3) {
		this(value1.openProvider(), value2.openProvider(), value3.openProvider());
	}
	
	public M3Provider(ValueProvider<T1> provider1, ValueProvider<T2> provider2, ValueProvider<T3> provider3) {
		this.provider1 = provider1;
		this.provider2 = provider2;
		this.provider3 = provider3;
	}
	
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public Group3<T1, T2, T3> get() {
		final var v1 = provider1.get();
		final var v2 = provider2.get();
		final var v3 = provider3.get();
		return new Group3<>(v1, v2, v3);
	}
	
	@Override
	public boolean isChenge() {
		return provider1.isChenge() || provider2.isChenge() || provider3.isChenge();
	}
	
	@Override
	public ValueProvider<Group3<T1, T2, T3>> copy() {
		return new M3Provider<>(provider1.copy(), provider2.copy(), provider3.copy());
	}
	
}
