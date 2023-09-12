package com.greentree.engine.moon.demo1;

import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.time.Time;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogFPSSystem implements InitSystem, UpdateSystem {

    private static final Logger LOG = LogManager.getLogger(LogFPSSystem.class);

    private static final float UPDATE_TIME = 2f;
    private Time time;
    private float t;
    private int fps;

    @ReadProperty({Time.class})
    @Override
    public void update() {
        t += time.delta();
        fps++;
        while (t > UPDATE_TIME) {
            t -= UPDATE_TIME;
            LOG.info("fps: " + fps / UPDATE_TIME);
            fps = 0;
        }
    }

    @ReadProperty({Time.class})
    @Override
    public void init(SceneProperties properties) {
        time = properties.get(Time.class);
    }

}
