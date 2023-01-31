package com.greentree.engine.moon.signals.ui;

import java.util.ArrayList;
import java.util.Collection;

import org.joml.Matrix4f;

import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.CreateComponent;
import com.greentree.engine.moon.ecs.annotation.ReadComponent;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.pool.EmptyEntityStrategy;
import com.greentree.engine.moon.ecs.pool.EntityPool;
import com.greentree.engine.moon.ecs.pool.StackEntityPool;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.signals.MouseButton;

public final class ClickableClickSystem implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final FilterBuilder CLICKABLES = new FilterBuilder().required(Clickable.class);
	private static final FilterBuilder CLICKS = new FilterBuilder().required(Click.class);
	private static final FilterBuilder CLICKABLE_CLICKS = new FilterBuilder()
			.required(ClickableClick.class);
	private final Collection<ClickableClick> clickEvent = new ArrayList<>();
	
	private Filter clickables, clicks, clickableClicks;
	
	private EntityPool pool;
	
	private World world;
	
	@Override
	public void destroy() {
		clickables.close();
		clicks.close();
		clickableClicks.close();
		world = null;
		pool.close();
	}
	
	@Override
	public void init(World world) {
		this.world = world;
		pool = new StackEntityPool(world, new EmptyEntityStrategy());
		clickables = CLICKABLES.build(world);
		clicks = CLICKS.build(world);
		clickableClicks = CLICKABLE_CLICKS.build(world);
	}
	
	@CreateComponent({ClickableClick.class})
	@ReadComponent({Click.class})
	@Override
	public void update() {
		for(var button : clickables) {
			final var t = button.get(Transform.class).getModelMatrix();
			
			final var temp = new Matrix4f();
			t.invert(temp);
			final var vec = new Vector3f();
			for(var e : clicks) {
				final var c = e.get(Click.class);
				if(c.button() != MouseButton.MOUSE_BUTTON_LEFT)
					continue;
				
				vec.set(c.x(), c.y(), 0);
				
				vec.mul(temp);
				
				if(-1 <= vec.x && vec.x <= 1 && -1 <= vec.y && vec.y <= 1) {
					click(button);
					break;
				}
			}
		}
		
		for(var c : clickableClicks)
			world.deleteEntity(c);
		for(var c : clickEvent) {
			final var e = pool.get();
			e.add(c);
		}
		clickEvent.clear();
	}
	
	private void click(Entity button) {
		clickEvent.add(new ClickableClick(button));
	}
	
	
	
}
