package com.greentree.engine.moon.assets.value.merge;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

public final class MIValue<T> implements Value<Iterable<T>> {
	
	private static final long serialVersionUID = 1L;
	
	private final Iterable<? extends Value<? extends T>> values;
	
	@SafeVarargs
	public MIValue(Value<? extends T>... values) {
		this(IteratorUtil.iterable(values));
	}
	
	public MIValue(Iterable<? extends Value<? extends T>> values) {
		this.values = values;
	}
	
	@Override
	public int characteristics() {
		return MIProvider.CHARACTERISTICS;
	}
	
	@Override
	public ValueProvider<Iterable<T>> openProvider() {
		return new MIProvider<>(values);
	}
	
	
}
