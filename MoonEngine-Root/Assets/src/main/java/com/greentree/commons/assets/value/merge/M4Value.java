package com.greentree.commons.assets.value.merge;

import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.provider.ValueProvider;

public final class M4Value<T1, T2, T3, T4> implements Value<Group4<T1, T2, T3, T4>> {
	
	private static final long serialVersionUID = 1L;
	
	private final Value<T1> Value1;
	private final Value<T2> Value2;
	private final Value<T3> Value3;
	private final Value<T4> Value4;
	
	public M4Value(Value<T1> Value1, Value<T2> Value2, Value<T3> Value3,
			Value<T4> Value4) {
		this.Value1 = Value1;
		this.Value2 = Value2;
		this.Value3 = Value3;
		this.Value4 = Value4;
	}
	
	@Override
	public int characteristics() {
		return M2Provider.CHARACTERISTICS;
	}
	
	@Override
	public ValueProvider<Group4<T1, T2, T3, T4>> openProvider() {
		return new M4Provider<>(Value1, Value2, Value3, Value4);
	}
	
}
