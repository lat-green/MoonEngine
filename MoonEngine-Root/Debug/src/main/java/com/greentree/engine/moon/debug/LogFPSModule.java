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

    private static final int DEFAULT_FRAME_TO_LOG = 10;

    private final int frameToLog;
    private Time time;
    private float t;
    private int fps;

    public LogFPSModule() {
        this(DEFAULT_FRAME_TO_LOG);
    }

    public LogFPSModule(int frameToLog) {
        this.frameToLog = frameToLog;
    }

    @ReadProperty({Time.class})
    @Override
    public void update() {
        t += time.delta();
        fps++;
        if (fps > frameToLog) {
            float ffps = frameToLog / t;
            LOG.info(String.format("fps: %7.3f ms: %6.2f", ffps, 1000f / ffps));
            fps -= frameToLog;
            t = 0;
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
