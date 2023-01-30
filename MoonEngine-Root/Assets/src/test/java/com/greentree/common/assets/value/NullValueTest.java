package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.value.NullValue;
import com.greentree.commons.assets.value.Value;

public class NullValueTest {
	
	@Test
	void test_NEW() {
		final var v1 = NullValue.instance();
		
		assertNull(v1.get());
		
		assertTrue(v1.hasCharacteristics(Value.CONST));
		assertTrue(v1.isNull());
	}
	
}
