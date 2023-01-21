package com.greentree.engine.moon.base;

import org.junit.jupiter.api.Test;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.ReadComponent;
import com.greentree.engine.moon.ecs.annotation.WriteComponent;
import com.greentree.engine.moon.ecs.component.Component;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.Systems;
import com.greentree.engine.moon.ecs.system.UpdateSystem;


public class Test1 {
	
	@Test
	void test1() {
		final var world = new World();
		
		final var e1 = world.newEntity();
		e1.add(new Position(5));
		e1.add(new Velocity(7));
		final var e2 = world.newEntity();
		e2.add(new Position(8));
		e2.add(new Velocity(8));
		
		final var systems = new Systems(new PhisicsSystem(), new PhisicsSystem(),
				new PhisicsSystem());
		
		systems.init(world);
		
		systems.update();
		
		systems.destroy();
	}
	
	//	@Test
	//	void test2() throws IOException, ClassNotFoundException {
	//
	//		final var world0 = new World();
	//		world0.newEntity();
	//		world0.newEntity();
	//
	//		final byte[] arr;
	//		try(final var bout = new ByteArrayOutputStream();
	//				final var  oout = new ObjectOutputStream(bout);) {
	//			oout.writeObject(world0);
	//			arr = bout.toByteArray();
	//		}
	//
	//		final World world;
	//		try(final var bin = new ByteArrayInputStream(arr);
	//				final var  oin = new ObjectInputStream(bin);) {
	//			world = (World) oin.readObject();
	//		}
	//
	//		assertEquals(world, world0);
	//	}
	
	
	private final static class PhisicsSystem implements InitSystem, UpdateSystem, DestroySystem {
		
		private static final FilterBuilder builder = new FilterBuilder().required(Position.class)
				.required(Velocity.class);
		private Filter filter;
		
		@Override
		public void destroy() {
			filter.close();
			filter = null;
		}
		
		@Override
		public void init(World world) {
			filter = builder.build(world);
		}
		
		
		
		@ReadComponent({Velocity.class})
		@WriteComponent({Position.class})
		@Override
		public void update() {
			synchronized(System.out) {
				System.out.println(Thread.currentThread().getName() + " Phisics");
			}
			for(var entity : filter)
				entity.get(Position.class).value += entity.get(Velocity.class).value;
		}
		
	}
	
	private final static class Position implements Component {
		
		private static final long serialVersionUID = 1L;
		public int value;
		
		public Position() {
		}
		
		public Position(int value) {
			this.value = value;
		}
		
		@Override
		public Position copy() {
			final var c = new Position();
			c.value = value;
			return c;
		}
		
		@Override
		public boolean copyTo(Component other) {
			final var c = (Position) other;
			c.value = value;
			return true;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Position [value=");
			builder.append(value);
			builder.append("]");
			return builder.toString();
		}
		
	}
	
	private final static class ShowPositionSystem
			implements InitSystem, UpdateSystem, DestroySystem {
		
		private static final FilterBuilder builder = new FilterBuilder().required(Position.class);
		private Filter filter;
		
		@Override
		public void destroy() {
			filter.close();
			filter = null;
		}
		
		
		
		@Override
		public void init(World world) {
			filter = builder.build(world);
		}
		
		@ReadComponent({Position.class})
		@Override
		public void update() {
			synchronized(System.out) {
				System.out.println(Thread.currentThread().getName() + " ShowPosition");
				for(var e : filter)
					System.out.println(e.get(Position.class));
			}
		}
		
	}
	
	private final static class ShowVelocitySystem
			implements InitSystem, UpdateSystem, DestroySystem {
		
		private static final FilterBuilder builder = new FilterBuilder().required(Velocity.class);
		private Filter filter;
		
		
		@Override
		public void destroy() {
			filter.close();
			filter = null;
		}
		
		
		
		@Override
		public void init(World world) {
			filter = builder.build(world);
		}
		
		@ReadComponent({Velocity.class})
		@Override
		public void update() {
			synchronized(System.out) {
				System.out.println(Thread.currentThread().getName() + " ShowVelocity");
				for(var e : filter)
					System.out.println(e.get(Velocity.class));
			}
		}
	}
	
	private final static class Velocity implements Component {
		
		private static final long serialVersionUID = 1L;
		
		public int value;
		
		public Velocity() {
		}
		
		public Velocity(int value) {
			this.value = value;
		}
		
		@Override
		public Velocity copy() {
			final var c = new Velocity();
			c.value = value;
			return c;
		}
		
		@Override
		public boolean copyTo(Component other) {
			final var c = (Velocity) other;
			c.value = value;
			return true;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Velocity [value=");
			builder.append(value);
			builder.append("]");
			return builder.toString();
		}
		
	}
	
}
