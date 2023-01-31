package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.engine.moon.assets.value.ConstValue;
import com.greentree.engine.moon.assets.value.DefaultValue;
import com.greentree.engine.moon.assets.value.MutableValue;
import com.greentree.engine.moon.assets.value.NullValue;
import com.greentree.engine.moon.assets.value.Value;

public class DefaultValueTest {
	
	private static final String TEXT1 = "A";
	private static final String TEXT2 = "B";
	
	@Test
	void test_NEW() {
		final var v1 = new MutableValue<>(TEXT1);
		final var v2 = new MutableValue<>(TEXT2);
		
		final var m = DefaultValue.newValue(v1, v2);
		
		assertFalse(m.hasCharacteristics(Value.CONST));
		assertFalse(m.isNull());
		
		try(final var p = m.openProvider()) {
			assertEquals(p.get(), TEXT1);
			v1.set(null);
			assertEquals(p.get(), TEXT2);
			v1.set(TEXT1);
			assertEquals(p.get(), TEXT1);
		}
	}
	
	@Test
	void test_NEW_of_ONE() {
		final var v1 = new MutableValue<>(TEXT1);
		
		final var m = DefaultValue.newValue(v1);
		
		assertTrue(m == v1);
	}
	
	@Test
	void test_NEW_of_ONE_and_NULL() {
		final var v1 = new MutableValue<>(TEXT1);
		final var v2 = NullValue.<String>instance();
		
		final var m = DefaultValue.newValue(v1, v2);
		
		assertEquals(m.get(), TEXT1);
	}
	
	@Test
	void test_NEW_of_CONST_and_MUTABLE() {
		final var v1 = ConstValue.newValue(TEXT1);
		final var v2 = new MutableValue<>(TEXT1);
		
		final var m = DefaultValue.newValue(v1, v2);
		
		assertTrue(m == v1);
	}
	
}
