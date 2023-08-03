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
        logger.debug(() -> "Begin Engine.launch");
        launchModule.launch(properties);
        logger.debug(() -> "End Engine.launch");
        while (!exitManager.isShouldExit()) {
            logger.debug(() -> "Begin Engine.update");
            updateModule.update();
            logger.debug(() -> "End Engine.update");
        }
        logger.debug(() -> "Begin Engine.terminate");
        terminateModule.terminate();
        logger.debug(() -> "End Engine.terminate");
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
