package com.greentree.common.ecs.pool;

import static com.greentree.common.ecs.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.greentree.common.ecs.ACompnent;
import com.greentree.common.ecs.Entity;
import com.greentree.common.ecs.World;

public class EntityPoolTest {

	static Stream<EntityPoolStrategy> strategies() {
		final var p = new Entity();
		p.add(new ACompnent());
		return Stream.of(new PrototypeEntityStrategy(p), new EmptyEntityStrategy());
	}

	@MethodSource("strategies")
	@DisplayName("strategies")
	@ParameterizedTest
	void testPool(EntityPoolStrategy str) {
		final var world = new World();

		try(final var pool = new StackEntityPool(world, str);) {
			final var e = pool.get();
			pool.free(e);
			final var g = pool.get();

			assertEquals(g, e);
		}
	}
	@MethodSource("strategies")
	@DisplayName("strategies After Serialization")
	@ParameterizedTest
	void testPool_after_deser(EntityPoolStrategy str)
			throws ClassNotFoundException, IOException {
		final var world = (World) deser(ser(new World()));

		try(final var pool = new StackEntityPool(world, str);) {
			final var e = pool.get();
			pool.free(e);
			final var g = pool.get();

			assertEquals(g, e);
		}
	}

}
