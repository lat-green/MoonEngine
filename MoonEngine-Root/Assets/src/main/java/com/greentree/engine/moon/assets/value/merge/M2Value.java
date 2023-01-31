package com.greentree.engine.moon.assets.value.merge;

import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

public final class M2Value<T1, T2> implements Value<Group2<T1, T2>> {
	
	private static final long serialVersionUID = 1L;
	
	private final Value<T1> Value1;
	private final Value<T2> Value2;
	
	public M2Value(Value<T1> Value1, Value<T2> Value2) {
		this.Value1 = Value1;
		this.Value2 = Value2;
	}
	
	@Override
	public int characteristics() {
		return M2Provider.CHARACTERISTICS;
	}
	
	@Override
	public ValueProvider<Group2<T1, T2>> openProvider() {
		return new M2Provider<>(Value1, Value2);
	}
	
	@Override
	public String toString() {
		return "M2Value [" + Value1 + ", " + Value2 + "]";
	}
	
}
