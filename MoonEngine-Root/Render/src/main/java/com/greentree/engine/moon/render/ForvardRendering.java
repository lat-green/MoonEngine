package com.greentree.engine.moon.render;

import com.greentree.commons.graphics.smart.shader.material.Material;
import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.base.component.WriteComponent;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import com.greentree.engine.moon.render.camera.CameraComponent;
import com.greentree.engine.moon.render.camera.CameraTarget;
import com.greentree.engine.moon.render.camera.CameraUtil;
import com.greentree.engine.moon.render.camera.SkyBoxComponent;
import com.greentree.engine.moon.render.light.HasShadow;
import com.greentree.engine.moon.render.light.direction.DirectionLightComponent;
import com.greentree.engine.moon.render.light.direction.DirectionLightTarget;
import com.greentree.engine.moon.render.light.point.PointLightComponent;
import com.greentree.engine.moon.render.light.point.PointLightTarget;
import com.greentree.engine.moon.render.mesh.MeshComponent;
import com.greentree.engine.moon.render.mesh.MeshRenderer;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static com.greentree.engine.moon.render.MaterialUtil.*;
import static com.greentree.engine.moon.render.mesh.MeshUtil.BOX;

public final class ForvardRendering implements WorldInitSystem, UpdateSystem {

    private static final float FAR_PLANE = 250;

    private static final FilterBuilder CAMERAS = new FilterBuilder().require(CameraTarget.class);
    private static final FilterBuilder POINT_LIGHT = new FilterBuilder().require(PointLightTarget.class);
    private static final FilterBuilder DIR_LIGTH = new FilterBuilder().require(DirectionLightTarget.class);
    private static final FilterBuilder RENDERER = new FilterBuilder().require(MeshRenderer.class);
    private final Material[] POINT_SHADOW = new Material[6];
    private Material SUPER_POINT_SHADOW;
    private Filter<? extends WorldEntity> cameras;
    private Filter<? extends WorldEntity> point_ligth;
    private Filter<? extends WorldEntity> dir_ligth;
    private Filter<? extends WorldEntity> renderer;
    private RenderLibrary library;

    @ReadProperty(RenderLibraryProperty.class)
    @Override
    public void init(World world, SceneProperties sceneProperties) {
        library = sceneProperties.get(RenderLibraryProperty.class).library();
        cameras = CAMERAS.build(world);
        point_ligth = POINT_LIGHT.build(world);
        dir_ligth = DIR_LIGTH.build(world);
        renderer = RENDERER.build(world);
        SUPER_POINT_SHADOW = library.build(getDefaultCubeMapShadowShader()).newMaterial();
        SUPER_POINT_SHADOW.put("far_plane", FAR_PLANE);
        final var shadowMatrices = getShadowMatrices();
        for (var i = 0; i < POINT_SHADOW.length; i++) {
            POINT_SHADOW[i] = SUPER_POINT_SHADOW.newMaterial();
            POINT_SHADOW[i].put("face", i);
            POINT_SHADOW[i].put("projectionView", shadowMatrices[i]);
        }
    }

    private static Matrix4f[] getShadowMatrices() {
        final var zeroVector = new Vector3f();
        var shadowMatrices = new Matrix4f[6];
        shadowMatrices[0] = new Matrix4f().lookAt(zeroVector, new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f));
        shadowMatrices[1] = new Matrix4f().lookAt(zeroVector, new Vector3f(-1.0f, 0.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f));
        shadowMatrices[2] = new Matrix4f().lookAt(zeroVector, new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 0.0f, 1.0f));
        shadowMatrices[3] = new Matrix4f().lookAt(zeroVector, new Vector3f(0.0f, -1.0f, 0.0f),
                new Vector3f(0.0f, 0.0f, -1.0f));
        shadowMatrices[4] = new Matrix4f().lookAt(zeroVector, new Vector3f(0.0f, 0.0f, 1.0f),
                new Vector3f(0.0f, -1.0f, 0.0f));
        shadowMatrices[5] = new Matrix4f().lookAt(zeroVector, new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, -1.0f, 0.0f));
        {
            var shadowProj = new Matrix4f(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0);
            for (var i = 0; i < 6; i++)
                shadowProj.mul(shadowMatrices[i], shadowMatrices[i]);
        }
        return shadowMatrices;
    }

    @ReadComponent(MeshComponent.class)
    @ReadComponent(Transform.class)
    @ReadComponent(SkyBoxComponent.class)
    @ReadComponent(CameraComponent.class)
    @ReadComponent(PointLightComponent.class)
    @ReadComponent(DirectionLightComponent.class)
    @ReadComponent(HasShadow.class)
    @WriteComponent({CameraTarget.class, DirectionLightTarget.class, PointLightTarget.class, CameraTarget.class})
    @Override
    public void update() {
        final var tempModelMatrix = new Matrix4f();
        for (var light : point_ligth)
            if (light.contains(HasShadow.class)) {
                SUPER_POINT_SHADOW.put("lightPos", light.get(Transform.class).position);
                final var target = light.get(PointLightTarget.class).target();
                final var buffer = target.buffer();
                buffer.clearDepth(1);
                buffer.enableCullFace();
                buffer.enableDepthTest();
                for (var m : renderer) {
                    final var mesh = m.get(MeshComponent.class).mesh().getValue();
                    buffer.bindMesh(mesh);
                    final var model = m.get(Transform.class).getModelMatrix(tempModelMatrix);
                    SUPER_POINT_SHADOW.put("model", model);
                    for (var properties : POINT_SHADOW) {
                        buffer.bindMaterial(properties.copy());
                        buffer.draw();
                    }
                }
                buffer.execute();
                buffer.clear();
            }
        for (var light : dir_ligth)
            if (light.contains(HasShadow.class)) {
                final var target = light.get(DirectionLightTarget.class).target();
                final var buffer = target.buffer();
                var shader = library.build(getDefaultShadowShader());
                buffer.clearDepth(1);
                buffer.enableCullFace();
                buffer.enableDepthTest();
                for (var m : renderer) {
                    final var mesh = m.get(MeshComponent.class).mesh().getValue();
                    buffer.bindMesh(mesh);
                    final var model = m.get(Transform.class).getModelMatrix(tempModelMatrix);
                    final var properties = shader.newMaterial();
                    mapShadowMaterial(properties);
                    properties.put("model", model);
                    buffer.bindMaterial(properties);
                    buffer.draw();
                }
                buffer.execute();
                buffer.clear();
            }
        for (var camera : cameras) {
            final var target = camera.get(CameraTarget.class).target();
            final var buffer = target.buffer();
            buffer.clearDepth(1);
            if (camera.contains(SkyBoxComponent.class)) {
                final var texture = camera.get(SkyBoxComponent.class).texture().getValue();
                buffer.bindMesh(library.build(BOX));
                buffer.drawSkyBox(library.build(getDefaultSkyBoxShader()), texture);
            }
            buffer.enableCullFace();
            buffer.enableDepthTest();
            for (var m : renderer) {
                final var mesh = m.get(MeshComponent.class).mesh().getValue();
                buffer.bindMesh(mesh);
                final var model = m.get(Transform.class).getModelMatrix(tempModelMatrix);
                final var material = m.get(MeshRenderer.class).material().getValue();
                final var properties = material.newMaterial();
                mapMaterial(properties);
                properties.put("model", model);
                buffer.bindMaterial(properties);
                buffer.draw();
            }
            buffer.execute();
            buffer.clear();
        }

    }

    private void mapShadowMaterial(Material properties) {
        mapMaterial0(properties);
    }

    private void mapMaterial(Material properties) {
        mapMaterial0(properties);
        {
            var i = 0;
            for (var light : point_ligth) {
                var name = "point_light[" + i + "].";
                properties.put(name + "position", light.get(Transform.class).position);
                properties.putRGB(name + "color", light.get(PointLightComponent.class).color());
                properties.put(name + "intensity", light.get(PointLightComponent.class).intensity());
                if (light.contains(HasShadow.class))
                    properties.put(name + "depth", light.get(PointLightTarget.class).target().getDepthTexture());
                i++;
            }
            properties.put("count_point_light", i);
        }
        {
            var i = 0;
            for (var light : dir_ligth) {
                var name = "dir_light[" + i + "].";
                properties.put(name + "direction", light.get(Transform.class).direction());
                properties.putRGB(name + "color", light.get(DirectionLightComponent.class).color());
                properties.put(name + "intensity", light.get(DirectionLightComponent.class).intensity());
                if (light.contains(HasShadow.class)) {
                    final Matrix4f lightSpaceMatrix;
                    final var view = CameraUtil.getView(light);
                    final var projection = light.get(DirectionLightComponent.class).getProjectionMatrix();
                    lightSpaceMatrix = new Matrix4f().identity().mul(projection).mul(view);
                    properties.put("lightSpaceMatrix[" + i + "]", lightSpaceMatrix);
                    properties.put(name + "depth", light.get(DirectionLightTarget.class).target().getDepthTexture());
                }
                i++;
            }
            properties.put("count_dir_light", i);
        }
    }

    private void mapMaterial0(Material properties) {
        properties.put("far_plane", FAR_PLANE);
    }

}
