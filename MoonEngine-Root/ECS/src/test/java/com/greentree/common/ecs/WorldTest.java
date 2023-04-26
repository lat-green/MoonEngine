package com.greentree.common.ecs;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.OneRequiredFilter;

public class WorldTest {
	
	
	
	@Test
	void event_AddEntity_on_newDeactiveEntity() {
		final var world = new World();
		
		world.onAddEntity(e-> {
			assertTrue(false);
		});
		
		world.newDeactiveEntity();
	}
	
	@Test
	void newDeactiveEntity_deleteEntity() {
		final var world = new World();
		final var e = world.newDeactiveEntity();
		world.deleteEntity(e);
	}
	
	@Test
	void newEntity_deactive_deleteEntity() {
		final var world = new World();
		final var e = world.newEntity();
		world.deactive(e);
		world.deleteEntity(e);
	}
	
	@Test
	void test_delete_not_my_Entity() {
		final var world = new World();
		final var e = new Entity();
		assertThrows(IllegalArgumentException.class, ()-> {
			world.deleteEntity(e);
		});
	}
	
	@Test
	void test1() {
		final var world = new World();
		final var e = world.newEntity();
		
		final var filter = new OneRequiredFilter(world, ACompnent.class);
		
		assertEquals(IteratorUtil.size(filter), 0, "OneRequiredFilter not work");
		
		e.add(new ACompnent());
		
		assertEquals(IteratorUtil.size(filter), 1, "OneRequiredFilter after add not work");
		
		world.deactive(e);
		
		assertEquals(IteratorUtil.size(filter), 0, "World.deactive not work");
		
		world.active(e);
		
		assertEquals(IteratorUtil.size(filter), 1, "World.active not work");
	}
	
	@Nested
	class Actives {
		
		@Test
		void isActive_isDeactive_deleteEntity() {
			final var world = new World();
			final var e = world.newEntity();
			world.deleteEntity(e);
			assertFalse(world.isDeactive(e));
			assertFalse(world.isActive(e));
		}
		
		@Test
		void isActive_isDeactive_new_Entity() {
			final var world = new World();
			final var e = new Entity();
			assertFalse(world.isDeactive(e));
			assertFalse(world.isActive(e));
		}
		
		@Test
		void isActive_isDeactive_newDeactiveEntity() {
			final var world = new World();
			final var e = world.newDeactiveEntity();
			assertTrue(world.isDeactive(e));
			assertFalse(world.isActive(e));
		}
		
		@Test
		void isActive_isDeactive_newEntity() {
			final var world = new World();
			final var e = world.newEntity();
			assertFalse(world.isDeactive(e));
			assertTrue(world.isActive(e));
		}
		
		@Test
		void isActive_isDeactive_newEntity_da() {
			final var world = new World();
			final var e = world.newEntity();
			world.deactive(e);
			world.active(e);
			assertFalse(world.isDeactive(e));
			assertTrue(world.isActive(e));
		}
		
	}
	
	@Nested
	class Events {
		
		@Test
		void onAddComponent_on_deactiveEntity() {
			final var world = new World();
			
			world.onAddComponent(c-> {
				assertTrue(false, "event World.onAddComponent after deactive");
			});
			
			final var e = world.newEntity();
			world.deactive(e);
			e.add(new ACompnent());
		}
		
		@Test
		void onAddComponent_on_newDeactiveEntity() {
			final var world = new World();
			
			world.onAddComponent(c-> {
				assertTrue(false);
			});
			
			final var e = world.newDeactiveEntity();
			e.add(new ACompnent());
		}
		
	}
	
	@Disabled
	@Nested
	class Timeouts {
		
		@Test
		void timeout_isActive() {
			final var world = new World();
			final var e = world.newEntity();
			assertTimeout(Duration.ofSeconds(2), ()-> {
				var t = 1_000_000;
				while(t-- > 0)
					world.isActive(e);
				world.clear();
			});
		}
		
		@Test
		void timeout_newDeactiveEntity() {
			final var world = new World(1_000_000);
			assertTimeout(Duration.ofSeconds(2), ()-> {
				var t = 1_000_000;
				while(t-- > 0)
					world.newDeactiveEntity();
			});
			world.clear();
		}
		
		@Test
		void timeout_newDeactiveEntity_deleteEntity() {
			final var world = new World();
			assertTimeout(Duration.ofSeconds(2), ()-> {
				var t = 1_000_000;
				while(t-- > 0) {
					final var e = world.newDeactiveEntity();
					world.deleteEntity(e);
				}
			});
			world.clear();
		}
		
		@Test
		void timeout_newEntity() {
			final var world = new World(1_000_000);
			assertTimeout(Duration.ofSeconds(4), ()-> {
				var t = 1_000_000;
				while(t-- > 0)
					world.newEntity();
			});
			world.clear();
		}
		
		@Test
		void timeout_newEntity_chunck() {
			final var world = new World();
			assertTimeout(Duration.ofSeconds(2), ()-> {
				var t = 1_000;
				while(t-- > 0) {
					var t0 = 1_000;
					while(t0-- > 0)
						world.newEntity();
					world.clear();
				}
			});
		}
		
		@Test
		void timeout_newEntity_deleteEntity() {
			final var world = new World();
			assertTimeout(Duration.ofSeconds(2), ()-> {
				var t = 1_000_000;
				while(t-- > 0) {
					final var e = world.newEntity();
					world.deleteEntity(e);
				}
			});
			world.clear();
		}
	}
	
}
