package com.greentree.engine.moon.render.camera;

import com.greentree.engine.moon.base.component.CreateComponent;
import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.base.property.world.ReadSceneProperty;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;
import com.greentree.engine.moon.render.pipeline.target.CameraRenderTarget;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CameraTargetGeneratedSystem implements WorldInitSystem, UpdateSystem {

    private static final Logger LOG = LogManager.getLogger(CameraTargetGeneratedSystem.class);
    private static final FilterBuilder CAMERAS = new FilterBuilder().require(CameraComponent.class)
            .ignore(CameraTarget.class);
    private Filter<? extends WorldEntity> cameras;
    private RenderLibrary context;

    @ReadComponent(CameraComponent.class)
    @CreateComponent(CameraTarget.class)
    @Override
    public void update() {
        for (var e : cameras) {
            final var c = e.get(CameraComponent.class);
            final var builder = context.createRenderTarget();
            builder.addColor2D();
            builder.addDepth();
            final var target = new CameraTarget(
                    new CameraRenderTarget(e, builder.build(c.width(), c.height())));
            e.add(target);
        }
    }

    @ReadSceneProperty({RenderLibraryProperty.class})
    @Override
    public void init(World world, SceneProperties properties) {
        cameras = CAMERAS.build(world);
        context = properties.get(RenderLibraryProperty.class).library();
    }

}
