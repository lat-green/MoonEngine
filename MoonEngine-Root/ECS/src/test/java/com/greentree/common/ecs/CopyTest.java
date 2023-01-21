package com.greentree.common.ecs;

import static com.greentree.common.ecs.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.greentree.engine.moon.ecs.World;

public class CopyTest {

	static Stream<Integer> ints() {
		return Stream.of(0, 9, 8, -13, 42);
	}

	@MethodSource("ints")
	@ParameterizedTest
	void ACompnent(int value) throws IOException, ClassNotFoundException {
		final var a = new ACompnent();
		a.value = value;
		final var c = a.copy();
		assertFalse(a == c);
		assertEquals(a, c);
	}

	@MethodSource("ints")
	@ParameterizedTest
	void Entity(int value) throws IOException, ClassNotFoundException {
		final var world = new World();
		final var entity = world.newEntity();
		
		final var a = new ACompnent();
		entity.add(a);
		a.value = value;
		final var c = entity.clone();
		
		assertComponentEquals(entity, c);
	}

	@MethodSource("ints")
	@ParameterizedTest
	void PrototypeEntity(int value) throws IOException, ClassNotFoundException {
		final var world = new World();
		final var entity = world.newEntity();
		{
    		final var a = new ACompnent();
    		entity.add(a);
    		a.value = value;
		}
		final var prototype = entity.clone();
		
		assertComponentEquals(entity, prototype);
		assertTrue(world.isActive(entity));
		assertFalse(world.isActive(prototype));

		final var ec = entity.clone(world);
		assertComponentEquals(entity, ec);
		assertTrue(world.isActive(ec));
		final var pc = prototype.clone(world);
		assertComponentEquals(prototype, pc);
		assertTrue(world.isActive(pc));
	}
	
}
