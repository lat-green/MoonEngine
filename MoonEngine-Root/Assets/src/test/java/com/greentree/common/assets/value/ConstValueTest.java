package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.engine.moon.assets.value.ConstValue;
import com.greentree.engine.moon.assets.value.Value;

public class ConstValueTest {
	
	private static final String TEXT = "A";
	
	@Test
	void test_NEW() {
		final var v1 = ConstValue.newValue(TEXT);
		assertTrue(v1.hasCharacteristics(Value.CONST));
		final var p = v1.openProvider();
		assertEquals(p.get(), TEXT);
		assertFalse(p.isNull());
	}
	
}
