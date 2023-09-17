package com.greentree.engine.moon.debug;

import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.time.Time;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogFPSSystem implements InitSystem, UpdateSystem {

    private static final Logger LOG = LogManager.getLogger(LogFPSSystem.class);

    private static final float DEFAULT_UPDATE_TIME = 1.1f;

    private final float updateTime;
    private Time time;
    private float t;
    private int fps;

    public LogFPSSystem() {
        this(DEFAULT_UPDATE_TIME);
    }

    public LogFPSSystem(float updateTime) {
        this.updateTime = updateTime;
    }

    @ReadProperty({Time.class})
    @Override
    public void update() {
        t += time.delta();
        fps++;
        while (t > updateTime) {
            t -= updateTime;
            float ffps = fps / updateTime;
            LOG.info(String.format("fps: %7.2f ms: %7.4f", ffps, 1 / ffps));
            fps = 0;
        }
    }

    @ReadProperty({Time.class})
    @Override
    public void init(SceneProperties properties) {
        t = 0;
        fps = 0;
        time = properties.get(Time.class);
    }

}
