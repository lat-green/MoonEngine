package com.greentree.engine.moon.render.camera;

import org.joml.Matrix4f;

public enum ProjectionMatrixEnum implements ProjectionMatrix {
    ORTHO() {
        @Override
        public Matrix4f getMatrix(float width, float height) {
            return OrthoCameraMatrix.INSTANCE.getMatrix(width, height);
        }
    };

    @Override
    public abstract Matrix4f getMatrix(float width, float height);
}
