package com.greentree.engine.moon.render.light.direction;

import com.greentree.engine.moon.base.systems.CreateComponent;
import com.greentree.engine.moon.base.systems.ReadComponent;
import com.greentree.engine.moon.base.systems.ReadWorldComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;

public class DirectionLightTargetGeneratedSystem implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final int SHADOW_SIZE = 256 * 4 * 4;
	private static final FilterBuilder CAMERAS = new FilterBuilder().required(DirectionLightComponent.class)
			.ignore(DirectionLightTarget.class);
	private Filter cameras;
	private RenderLibrary context;
	
	@ReadWorldComponent({RenderLibraryProperty.class})
	@Override
	public void init(World world) {
		cameras = CAMERAS.build(world);
		context = world.get(RenderLibraryProperty.class).library();
	}
	
	@Override
	public void destroy() {
		cameras.close();
		cameras = null;
		context = null;
	}
	
	@ReadComponent({DirectionLightComponent.class})
	@CreateComponent({DirectionLightTarget.class})
	@Override
	public void update() {
		for(var e : cameras) {
			final var builder = context.createRenderTarget();
			builder.addDepthTexture();
			final var target = new DirectionLightTarget(builder.build(SHADOW_SIZE, SHADOW_SIZE));
			e.add(target);
		}
	}
	
}
