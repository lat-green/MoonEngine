package com.greentree.engine.moon.render.light.point;

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

public class PointLightTargetGeneratedSystem implements WorldInitSystem, UpdateSystem {

    private static final int SHADOW_SIZE = 256 * 4 * 4;
    private static final FilterBuilder CAMERAS = new FilterBuilder().require(PointLightComponent.class)
            .ignore(PointLightTarget.class);
    private Filter<?> cameras;
    private RenderLibrary context;

    @ReadProperty({RenderLibraryProperty.class})
    @Override
    public void init(World world, SceneProperties properties) {
        cameras = CAMERAS.build(world);
        context = properties.get(RenderLibraryProperty.class).library();
    }

    @ReadComponent(PointLightComponent.class)
    @CreateComponent({PointLightTarget.class})
    @Override
    public void update() {
        for (var e : cameras) {
            final var builder = context.createRenderTarget();
            builder.addDepthCubeMapTexture();
            final var target = new PointLightTarget(builder.build(SHADOW_SIZE, SHADOW_SIZE));
            e.add(target);
        }
    }

}
