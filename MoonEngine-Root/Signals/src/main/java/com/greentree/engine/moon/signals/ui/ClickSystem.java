package com.greentree.engine.moon.signals.ui;

import java.util.ArrayList;
import java.util.Collection;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.annotation.CreateComponent;
import com.greentree.common.ecs.annotation.DestroyComponent;
import com.greentree.common.ecs.annotation.ReadWorldComponent;
import com.greentree.common.ecs.filter.Filter;
import com.greentree.common.ecs.filter.FilterBuilder;
import com.greentree.common.ecs.pool.EmptyEntityStrategy;
import com.greentree.common.ecs.pool.EntityPool;
import com.greentree.common.ecs.pool.StackEntityPool;
import com.greentree.common.ecs.system.DestroySystem;
import com.greentree.common.ecs.system.InitSystem;
import com.greentree.common.ecs.system.UpdateSystem;
import com.greentree.common.renderer.window.MouseButton;
import com.greentree.commons.action.ListenerCloser;
import com.greentree.engine.moon.base.scene.EnginePropertiesWorldComponent;
import com.greentree.engine.moon.render.ui.Click;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.device.Devices;
import com.greentree.engine.moon.signals.mouse.MouseButtonDevice;
import com.greentree.engine.moon.signals.mouse.MouseXDevice;
import com.greentree.engine.moon.signals.mouse.MouseYDevice;


public final class ClickSystem implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final FilterBuilder CLICKS = new FilterBuilder().required(Click.class);
	
	private Filter clicks;
	
	private EntityPool pool;
	
	private World world;
	
	private Devices devices;
	
	private final Collection<Click> clickEvent = new ArrayList<>();
	
	private ListenerCloser lc1, lc2, lc3;
	
	@Override
	public void destroy() {
		clicks.close();
		lc1.close();
		lc2.close();
		lc3.close();
		clickEvent.clear();
	}
	
	@ReadWorldComponent({EnginePropertiesWorldComponent.class})
	@Override
	public void init(World world) {
		this.world = world;
		pool = new StackEntityPool(world, new EmptyEntityStrategy());
		clicks = CLICKS.build(world);
		
		devices = world.get(EnginePropertiesWorldComponent.class).properties()
				.get(DevicesProperty.class).devices();
		lc1 = addListener(MouseButton.MOUSE_BUTTON_LEFT);
		lc2 = addListener(MouseButton.MOUSE_BUTTON_RIGHT);
		lc3 = addListener(MouseButton.MOUSE_BUTTON_MIDDLE);
	}
	
	@CreateComponent({Click.class})
	@DestroyComponent({Click.class})
	@Override
	public void update() {
		for(var c : clicks)
			world.deleteEntity(c);
		for(var c : clickEvent) {
			final var e = pool.get();
			e.add(c);
		}
		clickEvent.clear();
	}
	
	private ListenerCloser addListener(MouseButton button) {
		return devices.addListener(new MouseButtonDevice(button), t-> {
			if(t.value())
				click(button);
		});
	}
	
	private void click(MouseButton button) {
		final var x = devices.getValue(new MouseXDevice()).value();
		final var y = devices.getValue(new MouseYDevice()).value();
		
		clickEvent.add(new Click(x, y, button));
		
	}
	
}
