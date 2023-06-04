package com.greentree.engine.moon.render.light.direction;

import com.greentree.engine.moon.base.component.CreateComponent;
import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;

public class DirectionLightTargetGeneratedSystem implements WorldInitSystem, UpdateSystem {

    private static final int SHADOW_SIZE = 256 * 4 * 4;
    private static final FilterBuilder CAMERAS = new FilterBuilder().require(DirectionLightComponent.class)
            .ignore(DirectionLightTarget.class);
    private Filter<?> cameras;
    private RenderLibrary context;

    @ReadComponent(DirectionLightComponent.class)
    @CreateComponent({DirectionLightTarget.class})
    @Override
    public void update() {
        for (var e : cameras) {
            final var builder = context.createRenderTarget();
            builder.addDepthTexture();
            final var target = new DirectionLightTarget(builder.build(SHADOW_SIZE, SHADOW_SIZE));
            e.add(target);
        }
    }

    @ReadProperty({RenderLibraryProperty.class})
    @Override
    public void init(World world, SceneProperties properties) {
        cameras = CAMERAS.build(world);
        context = properties.get(RenderLibraryProperty.class).library();
    }

}
