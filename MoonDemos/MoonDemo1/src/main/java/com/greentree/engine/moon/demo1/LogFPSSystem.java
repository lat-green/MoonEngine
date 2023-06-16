package com.greentree.engine.moon.demo1;

import com.greentree.engine.moon.base.name.Names;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.time.Time;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class LogFPSSystem implements InitSystem, UpdateSystem {

    private static final float UPDATE_TIME = 2f;
    private Time time;
    private float t;
    private int fps;
    private WorldEntity camera;

    @ReadProperty({Time.class})
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

    @ReadProperty({Time.class})
    @Override
    public void init(SceneProperties properties) {
        camera = properties.get(Names.class).get("camera");
        time = properties.get(Time.class);
    }

}
