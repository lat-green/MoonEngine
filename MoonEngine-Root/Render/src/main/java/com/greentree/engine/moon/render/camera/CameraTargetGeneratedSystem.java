package com.greentree.engine.moon.render.camera;

import com.greentree.commons.graphics.smart.RenderContext;
import com.greentree.engine.moon.base.component.CreateComponent;
import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import com.greentree.engine.moon.render.pipeline.RenderContextProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.greentree.commons.image.PixelFormat.RGB;

public class CameraTargetGeneratedSystem implements WorldInitSystem, UpdateSystem {

    private static final Logger LOG = LogManager.getLogger(CameraTargetGeneratedSystem.class);
    private static final FilterBuilder CAMERAS = new FilterBuilder().require(CameraComponent.class)
            .ignore(CameraTarget.class);
    private Filter<? extends WorldEntity> cameras;
    private RenderContext context;

    @ReadComponent(CameraComponent.class)
    @CreateComponent(CameraTarget.class)
    @Override
    public void update() {
        for (var e : cameras) {
            final var c = e.get(CameraComponent.class);
            final var builder = context.newFrameBuffer().addColor2D(RGB).addDepth();
            final var target = new CameraTarget(builder.build(c.width(), c.height()));
            e.add(target);
        }
    }

    @ReadProperty({RenderContextProperty.class})
    @Override
    public void init(World world, SceneProperties properties) {
        cameras = CAMERAS.build(world);
        context = properties.get(RenderContextProperty.class).value();
    }

}
