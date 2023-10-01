package com.greentree.engine.moon.render.pipeline.target.buffer;

import com.greentree.commons.graphics.smart.mesh.Mesh;
import com.greentree.commons.graphics.smart.shader.material.Material;
import com.greentree.commons.image.Color;

import java.util.Objects;

@Deprecated
public class ProxyCommandBuffer implements TargetCommandBuffer {

    protected final TargetCommandBuffer origin;

    protected ProxyCommandBuffer(TargetCommandBuffer origin) {
        Objects.requireNonNull(origin);
        this.origin = origin;
    }

    @Override
    public void clear() {
        origin.clear();
    }

    @Override
    public void clear(Color color, float depth) {
        origin.clear(color, depth);
    }

    @Override
    public void clearColor(Color color) {
        origin.clearColor(color);
    }

    @Override
    public void clearDepth(float depth) {
        origin.clearDepth(depth);
    }

    @Override
    public void disableCullFace() {
        origin.disableCullFace();
    }

    @Override
    public void disableDepthTest() {
        origin.disableDepthTest();
    }

    @Override
    public void bindMesh(Mesh mesh) {
        origin.bindMesh(mesh);
    }

    @Override
    public void bindMaterial(Material material) {
        origin.bindMaterial(material);
    }

    @Override
    public void draw() {
        origin.draw();
    }

    @Override
    public void enableCullFace() {
        origin.enableCullFace();
    }

    @Override
    public void enableDepthTest() {
        origin.enableDepthTest();
    }

    @Override
    public void execute() {
        origin.execute();
    }

}
