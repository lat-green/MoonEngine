package com.greentree.engine.moon.assets.value.merge;

import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;


public final class M4Provider<T1, T2, T3, T4> implements ValueProvider<Group4<T1, T2, T3, T4>> {
	
	public static final int CHARACTERISTICS = NOT_NULL;
	private final ValueProvider<T1> provider1;
	private final ValueProvider<T2> provider2;
	private final ValueProvider<T3> provider3;
	private final ValueProvider<T4> provider4;
	
	public M4Provider(Value<T1> Value1, Value<T2> Value2, Value<T3> Value3,
			Value<T4> Value4) {
		this.provider1 = Value1.openProvider();
		this.provider2 = Value2.openProvider();
		this.provider3 = Value3.openProvider();
		this.provider4 = Value4.openProvider();
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
		provider4.close();
	}
	
	@Override
	public Group4<T1, T2, T3, T4> get() {
		final var v1 = provider1.get();
		final var v2 = provider2.get();
		final var v3 = provider3.get();
		final var v4 = provider4.get();
		return new Group4<>(v1, v2, v3, v4);
	}
	
	@Override
	public boolean isChenge() {
		return provider1.isChenge() || provider2.isChenge() || provider3.isChenge()
				|| provider4.isChenge();
	}
}
