package com.greentree.engine.moon.base.time;

import com.greentree.engine.moon.base.property.world.CreateSceneProperty;
import com.greentree.engine.moon.base.property.world.WriteSceneProperty;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class TimeSystem implements InitSystem, UpdateSystem {

    private Time time;

    @WriteSceneProperty({Time.class})
    @Override
    public void update() {
        time.step();
    }

    @CreateSceneProperty({Time.class})
    @Override
    public void init(SceneProperties properties) {
        this.time = new Time();
        properties.add(time);
    }

}
