package com.greentree.engine.moon.assets.value.merge;

import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;


public final class M3Provider<T1, T2, T3> implements ValueProvider<Group3<T1, T2, T3>> {
	
	public static final int CHARACTERISTICS = NOT_NULL;
	private final ValueProvider<T1> provider1;
	private final ValueProvider<T2> provider2;
	private final ValueProvider<T3> provider3;
	
	public M3Provider(Value<T1> Value1, Value<T2> Value2, Value<T3> Value3) {
		this.provider1 = Value1.openProvider();
		this.provider2 = Value2.openProvider();
		this.provider3 = Value3.openProvider();
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public void close() {
		provider1.close();
		provider2.close();
		provider3.close();
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
	
}
