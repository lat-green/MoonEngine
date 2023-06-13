package com.greentree.engine.moon.render.camera;

import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

@RequiredComponent({Transform.class})
public record CameraComponent(int width, int height, Matrix4fc projection)
        implements ConstComponent {

    public CameraComponent(int width, int height, ProjectionMatrixEnum projectionEnum) {
        this(width, height, projectionEnum.getMatrix(width, height));
    }

    public CameraComponent(int width, int height, int size) {
        this(width, height, new OrthoCameraMatrix(size));
    }

    public CameraComponent(int width, int height, ProjectionMatrix matrix) {
        this(width, height, matrix.getMatrix(width, height));
    }

    public CameraComponent(int width, int height) {
        this(width, height, FrustumCameraMatrix.INSTANCE);
    }

    public Matrix4f getProjectionMatrix() {
        return getProjectionMatrix(new Matrix4f());
    }

    public Matrix4f getProjectionMatrix(Matrix4f dest) {
        return dest.set(projection);
    }

}
