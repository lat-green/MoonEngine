package com.greentree.commons.assets.value.provider;

import java.util.Objects;

public record ConstProvider<T>(T value) implements ValueProvider<T> {
	
	public static final int CHARACTERISTICS = CONST | NOT_NULL | BLANCK_CLOSE | DISTINCT_CHANGE;
	
	public ConstProvider {
		Objects.requireNonNull(value);
	}
	
	@Override
	public String toString() {
		return "ConstProvider [" + value + "]";
	}
	
	@Override
	public void close() {
	}
	
	@Override
	public boolean isChenge() {
		return false;
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public T get() {
		return value;
	}
	
}
