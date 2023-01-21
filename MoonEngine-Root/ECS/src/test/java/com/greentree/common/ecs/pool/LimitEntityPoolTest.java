package com.greentree.common.ecs.pool;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.pool.ArrayLimitEntityPool;
import com.greentree.engine.moon.ecs.pool.EmptyEntityStrategy;

public class LimitEntityPoolTest {
	
	@Test
	void test0() {
		final var world = new World();
		
		final var lc = world.onAddEntity(c-> {
			assertTrue(false);
		});
		
		try(final var pool = new ArrayLimitEntityPool(world, 1,
				new EmptyEntityStrategy())) {
			lc.close();
		}
	}
	
	@Test
	void test1() {
		final var world = new World();
		try(final var pool = new ArrayLimitEntityPool(world, 3,
				new EmptyEntityStrategy())) {
			
			assertNotNull(pool.get());
			assertNotNull(pool.get());
			assertNotNull(pool.get());
			assertNull(pool.get());
			assertNull(pool.get());
			assertNull(pool.get());
			
		}
		
	}
	
	
	@Test
	void test2() {
		final var world = new World();
		try(final var pool = new ArrayLimitEntityPool(world, 3,
				new EmptyEntityStrategy())) {
			final var e1 = pool.get();
			final var e2 = pool.get();
			final var e3 = pool.get();
			
			pool.free(e1);
			pool.free(e2);
			pool.free(e3);
			
			final var e4 = pool.get();
			final var e5 = pool.get();
			final var e6 = pool.get();
			assertNull(pool.get());
			
			assertNotNull(e4);
			assertNotNull(e5);
			assertNotNull(e6);
		}
		
	}
	
	@Test
	void test3() {
		final var world = new World();
		try(final var pool = new ArrayLimitEntityPool(world, 1,
				new EmptyEntityStrategy())) {
			
			final var e1 = pool.get();
			pool.free(e1);
			final var e2 = pool.get();
			
			assertEquals(e1, e2);
		}
	}
	
	@Test
	void test4() {
		final var world = new World();
		try(final var pool = new ArrayLimitEntityPool(world, 1,
				new EmptyEntityStrategy())) {
			
			final var e1 = pool.get();
			pool.free(e1);
			
			world.deleteEntity(e1);
			
			final var e2 = pool.get();
			assertNull(pool.get());
			
			assertNotNull(e2);
			
			assertNotEquals(e1, e2);
		}
	}
	
}
