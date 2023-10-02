package com.greentree.engine.moon.render.scene;

import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import com.greentree.engine.moon.render.camera.CameraTarget;
import com.greentree.engine.moon.render.light.direction.DirectionLightComponent;
import com.greentree.engine.moon.render.light.point.PointLightComponent;
import com.greentree.engine.moon.render.mesh.MeshRenderer;

public class RenderSceneConfig implements WorldInitSystem {

    private static final FilterBuilder CAMERAS = new FilterBuilder().require(CameraTarget.class);
    private static final FilterBuilder POINT_LIGHT = new FilterBuilder().require(PointLightComponent.class);
    private static final FilterBuilder DIR_LIGHT = new FilterBuilder().require(DirectionLightComponent.class);
    private static final FilterBuilder RENDERER = new FilterBuilder().require(MeshRenderer.class);
    private Filter<? extends WorldEntity> cameras;
    private Filter<? extends WorldEntity> pointLight;
    private Filter<? extends WorldEntity> dirLight;
    private Filter<? extends WorldEntity> renderer;

    @CreateProperty(RenderSceneProperty.class)
    @Override
    public void init(World world, SceneProperties sceneProperties) {
        cameras = CAMERAS.build(world);
        pointLight = POINT_LIGHT.build(world);
        dirLight = DIR_LIGHT.build(world);
        renderer = RENDERER.build(world);
        var scene = new ECSRenderScene(cameras, renderer, pointLight, dirLight);
        sceneProperties.add(new RenderSceneProperty(scene));
    }

}
