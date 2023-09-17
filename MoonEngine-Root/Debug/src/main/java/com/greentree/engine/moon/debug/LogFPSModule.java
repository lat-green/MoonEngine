package com.greentree.engine.moon.debug;

import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.time.Time;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.UpdateModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogFPSModule implements LaunchModule, UpdateModule {

    private static final Logger LOG = LogManager.getLogger(LogFPSModule.class);

    private static final float DEFAULT_UPDATE_TIME = 1.1f;

    private final float updateTime;
    private Time time;
    private float t;
    private int fps;

    public LogFPSModule() {
        this(DEFAULT_UPDATE_TIME);
    }

    public LogFPSModule(float updateTime) {
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
            LOG.info(String.format("fps: %8.3f ms: %10.2f", ffps, 1000f / ffps));
            fps = 0;
        }
    }

    @ReadProperty({Time.class})
    @Override
    public void launch(EngineProperties properties) {
        t = 0;
        fps = 0;
        time = properties.get(Time.class);
    }

}
