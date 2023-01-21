package com.greentree.engine.moon.render;

import com.greentree.common.renderer.pipeline.ForvardRendering;
import com.greentree.common.renderer.pipeline.RenderContext;
import com.greentree.common.renderer.pipeline.RenderPipeline;
import com.greentree.common.renderer.scene.SceneContext;
import com.greentree.engine.moon.base.scene.EnginePropertiesWorldComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.ReadWorldComponent;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.render.scene.RenderSceneWorldComponent;

public class RenderSystem implements InitSystem, DestroySystem, UpdateSystem {
	
	private static final RenderPipeline PIPELINE = new ForvardRendering();
	
	private SceneContext scene;
	
	private RenderContext context;
	
	@Override
	public void destroy() {
		scene = null;
		context = null;
	}
	
	@ReadWorldComponent({EnginePropertiesWorldComponent.class,RenderSceneWorldComponent.class})
	@Override
	public void init(World world) {
		context = world.get(EnginePropertiesWorldComponent.class).properties().get(RenderContextProperty.class).context();
		scene = world.get(RenderSceneWorldComponent.class).context();
	}
	
	@Override
	public void update() {
		PIPELINE.render(scene, context);
	}
	
}
