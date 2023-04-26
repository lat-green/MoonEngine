package com.greentree.engine.moon.editor;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.ReadComponent;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.editor.ui.Button;
import com.greentree.engine.moon.signals.ui.ClickableClick;

public class LogSystem implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final FilterBuilder CLICKS = new FilterBuilder()
			.required(ClickableClick.class);
	private Filter clicks;
	
	@Override
	public void destroy() {
		clicks.close();
	}
	
	@Override
	public void init(World world) {
		clicks = CLICKS.build(world);
		
	}
	
	@ReadComponent({ClickableClick.class})
	@Override
	public void update() {
		for(var e : clicks) {
			final var c = e.get(ClickableClick.class);
			System.out.println(c.button().get(Button.class).name());
		}
	}
	
}
