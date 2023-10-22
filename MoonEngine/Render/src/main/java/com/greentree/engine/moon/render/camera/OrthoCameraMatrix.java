package com.greentree.engine.moon.render.camera;

import org.joml.Matrix4f;

public final class OrthoCameraMatrix implements ProjectionMatrix {

    public static final OrthoCameraMatrix INSTANCE = new OrthoCameraMatrix();
    private static final long serialVersionUID = 1L;
    private final float size;

    private OrthoCameraMatrix() {
        this(1);
    }

    public OrthoCameraMatrix(float size) {
        super();
        this.size = size;
    }

    public float getSize() {
        return size;
    }

    @Override
    public Matrix4f getMatrix(float width, float height) {
        final var w = size;
        final float h = w;
//        final float h = height / width * w;
        return new Matrix4f().ortho(-w, w, -h, h, 0.0F, 10000.0F);
    }

}
