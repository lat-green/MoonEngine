package com.greentree.engine.moon.modules;

import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.modules.property.EnginePropertiesBase;
import com.greentree.engine.moon.modules.property.ExitManagerProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Engine {

    private static final Logger logger = LogManager.getLogger(Engine.class);

    private Engine() {
    }

    public static EngineProperties launch(String[] args, LaunchModule launchModule, UpdateModule updateModule,
                                          TerminateModule terminateModule) {
        var exitManager = new ExitManagerImpl();
        var properties = new EnginePropertiesBase();
        properties.add(new ExitManagerProperty(exitManager));
        logger.trace("Begin Engine.launch");
        launchModule.launch(properties);
        logger.trace("End Engine.launch");
        while (!exitManager.isShouldExit()) {
            logger.trace("Begin Engine.update");
            updateModule.update();
            logger.trace("End Engine.update");
        }
        logger.trace("Begin Engine.terminate");
        terminateModule.terminate();
        logger.trace("End Engine.terminate");
        return properties;
    }

}

class ExitManagerImpl implements ExitManager {

    private boolean shouldExit = false;

    @Override
    public void exit() {
        shouldExit = true;
    }

    @Override
    public boolean isShouldExit() {
        return shouldExit;
    }

}
