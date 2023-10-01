package com.greentree.engine.moon.render.pipeline.target.buffer;

import com.greentree.commons.graphics.smart.shader.Shader;
import com.greentree.commons.graphics.smart.shader.material.Material;
import com.greentree.commons.graphics.smart.texture.Texture;
import com.greentree.commons.math.Mathf;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.render.camera.CameraComponent;
import com.greentree.engine.moon.render.camera.CameraUtil;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Objects;

public final class CameraCommandBuffer extends ProxyCommandBuffer {

    private final Entity camera;

    public CameraCommandBuffer(Entity camera, TargetCommandBuffer base) {
        super(base);
        Objects.requireNonNull(camera);
        this.camera = camera;
    }

    @Override
    public void bindMaterial(Material properties) {
        properties = properties.newMaterial();
        final var transform = this.camera.get(Transform.class);
        final var view = CameraUtil.getView(camera);
        final var projection = camera.get(CameraComponent.class).getProjectionMatrix();
        final var viewProjection = new Matrix4f().identity().mul(projection).mul(view);
        properties.put("projectionView", viewProjection);
        properties.put("viewPos", transform.position);
        super.bindMaterial(properties);
    }

    public void drawSkyBox(Shader shader, Texture texture) {
        final var transform = this.camera.get(Transform.class);
        final var camera = this.camera.get(CameraComponent.class);
        Matrix4f veiwProjection;
        {
            float w = camera.width();
            float h = camera.height();
            float wh = w / h;
            veiwProjection = new Matrix4f().perspective(Mathf.PIHalf / wh, wh, 0, 1);
        }
        Matrix3f mat33 = new Matrix3f();
        Matrix4f view = CameraUtil.getView(this.camera);
        veiwProjection.mul(new Matrix4f(view.get3x3(mat33)));
        final var properties = shader.newMaterial();
        properties.put("skybox", texture);
        properties.put("projectionView", veiwProjection);
        properties.put("viewPos", transform.position);
        super.bindMaterial(properties);
        draw();
    }

}
