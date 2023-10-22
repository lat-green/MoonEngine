package com.greentree.engine.moon.monkey;

import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.base.name.Names;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class BackGroundSystem implements InitSystem, UpdateSystem {

    private Vector3f bg1, bg2, camera;

    @Override
    public void init(SceneProperties properties) {
        var names = properties.get(Names.class);
        bg1 = names.get("bg1").get(Transform.class).position;
        bg2 = names.get("bg2").get(Transform.class).position;
        camera = names.get("camera").get(Transform.class).position;
    }

    @Override
    public void update() {
        if (camera.x() - bg1.x() > 2) {
            bg1.x(bg1.x() + 2);
            bg2.x(bg1.x() + 2);
        }
    }

}
