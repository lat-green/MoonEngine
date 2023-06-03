package com.greentree.engine.moon.render.camera;

import com.greentree.engine.moon.base.component.CreateSystem;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.ClassSetEntity;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.scene.SceneProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@CreateSystem(CameraSystem.class)
public final class Cameras implements SceneProperty {

    private static final Logger LOG = LogManager.getLogger(Cameras.class);

    private Entity mainCamera;

    public Entity main() {
        if (mainCamera == null) {
            LOG.error("Create temp camera");
            final var c = new ClassSetEntity();
            try (final var lock = c.lock()) {
                lock.add(new Transform());
                lock.add(new CameraComponent(1, 1, FrustumCameraMatrix.INSTANCE));
            }
            return c;
        }
        return mainCamera;
    }

    void setMainCamera(Entity mainCamera) {
        this.mainCamera = mainCamera;
    }

}
