package com.greentree.engine.moon.monkey;

import com.greentree.engine.moon.base.name.Names;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class RenderSystem implements InitSystem, UpdateSystem {

    private SceneProperties sceneProperties;

    @Override
    public void update() {
        //		var palyer = world.get(Names.class).get("player");
        //		System.out.println(palyer);
        var camera = sceneProperties.get(Names.class).get("camera");
        System.out.println(camera.get(Transform.class).rotation);
        System.out.println();
    }

    @Override
    public void init(SceneProperties sceneProperties) {
        this.sceneProperties = sceneProperties;
    }

}
