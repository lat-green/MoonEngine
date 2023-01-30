package com.greentree.commons.assets.value.merge;

import java.util.ArrayList;
import java.util.Collection;

import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.provider.ValueProvider;


public final class MIProvider<T> implements ValueProvider<Iterable<T>> {
	
	public static final int CHARACTERISTICS = NOT_NULL;
	private final Collection<ValueProvider<? extends T>> providers;
	
	public MIProvider(Iterable<? extends Value<? extends T>> Values) {
		providers = new ArrayList<>();
		for(var s : Values)
			providers.add(s.openProvider());
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public void close() {
		for(var p : providers)
			p.close();
	}
	
	@Override
	public Iterable<T> get() {
		final var result = new ArrayList<T>();
		for(var p : providers)
			result.add(p.get());
		return result;
	}
	
	@Override
	public boolean isChenge() {
		for(var p : providers)
			if(p.isChenge())
				return p.isChenge();
		return false;
	}
	
	@Override
	public String toString() {
		return "MIProvider " + providers;
	}
	
}
