package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.engine.moon.assets.value.MutableValue;
import com.greentree.engine.moon.assets.value.Value;

public class MutableValueTest {
	
	private static final String TEXT = "A";
	
	@Test
	void test_NEW() {
		final var v1 = new MutableValue<>(TEXT);
		try(final var p = v1.openProvider()) {
			assertEquals(p.get(), TEXT);
			
			assertFalse(p.hasCharacteristics(Value.CONST));
			assertFalse(p.isNull());
			
			v1.set(null);
			
			assertTrue(p.isNull());
			assertNull(p.get());
			
			assertFalse(p.isChenge());
			assertFalse(p.isChenge());
			v1.set(TEXT);
			assertTrue(p.isChenge());
			assertTrue(p.isChenge());
			assertNotNull(p.get());
			assertFalse(p.isChenge());
			assertFalse(p.isChenge());
			v1.set(TEXT);
			assertTrue(p.isChenge());
			assertTrue(p.isChenge());
		}
	}
}
