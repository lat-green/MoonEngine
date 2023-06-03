package com.greentree.engine.moon.demo1;

import com.greentree.engine.moon.base.property.world.ReadSceneProperty;
import com.greentree.engine.moon.base.time.Time;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class LogFPSSystem implements InitSystem, UpdateSystem {

    private static final float UPDATE_TIME = 2f;
    private Time time;
    private float t;
    private int fps;

    @ReadSceneProperty({Time.class})
    @Override
    public void update() {
        t += time.delta();
        fps++;
        while (t > UPDATE_TIME) {
            t -= UPDATE_TIME;
            System.out.println(fps / UPDATE_TIME);
            fps = 0;
        }
    }

    @ReadSceneProperty({Time.class})
    @Override
    public void init(SceneProperties properties) {
        time = properties.get(Time.class);
    }

}
