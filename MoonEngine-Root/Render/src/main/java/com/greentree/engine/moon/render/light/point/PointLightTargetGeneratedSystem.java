package com.greentree.engine.moon.render.light.point;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.CreateComponent;
import com.greentree.engine.moon.ecs.annotation.ReadComponent;
import com.greentree.engine.moon.ecs.annotation.ReadWorldComponent;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;

public class PointLightTargetGeneratedSystem implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final int SHADOW_SIZE = 256 * 4 * 4;
	private static final FilterBuilder CAMERAS = new FilterBuilder().required(PointLightComponent.class)
			.ignore(PointLightTarget.class);
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
	
	@ReadComponent({PointLightComponent.class})
	@CreateComponent({PointLightTarget.class})
	@Override
	public void update() {
		for(var e : cameras) {
			final var builder = context.createRenderTarget();
			builder.addDepthCubeMapTexture();
			final var target = new PointLightTarget(builder.build(SHADOW_SIZE, SHADOW_SIZE));
			e.add(target);
		}
	}
	
}
