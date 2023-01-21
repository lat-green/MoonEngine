package com.greentree.common.ecs;

import static com.greentree.common.ecs.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.greentree.engine.moon.ecs.Entity;

public class SerializableTest {

	static Stream<Integer> ints() {
		return Stream.of(0, 9, 8, -13, 42);
	}

	@MethodSource("ints")
	@ParameterizedTest
	void ACompnent_ser_deser(int value) throws IOException, ClassNotFoundException {
		final var a = new ACompnent();
		a.value = value;
		final var ser = ser(a);
		final var c = deser(ser);
		assertFalse(a == c);
		assertEquals(a, c);
	}
	
	@MethodSource("ints")
	@ParameterizedTest
	void Entity_ser_deser(int value) throws IOException, ClassNotFoundException {
		final var a = new ACompnent();
		a.value = value;
		final var entity = new Entity();
		entity.add(a);
		final var ser = ser(entity);
		final var c = deser(ser);
		assertComponentEquals(entity, (Entity)c);
	}
	

	@DisplayName("Entity Actions After Serialization")
	@Test
	void test1() throws ClassNotFoundException, IOException {
		final var e = (Entity) deser(ser(new Entity()));
		final var isCall = new boolean[1];
		final var c = new ACompnent();
		e.getAddAction().addListener(c0 -> {
			assertFalse(isCall[0]);
			isCall[0] = true;
			assertEquals(c0, c);
		});
		e.add(c);
		assertTrue(isCall[0]);
	}
	
}
