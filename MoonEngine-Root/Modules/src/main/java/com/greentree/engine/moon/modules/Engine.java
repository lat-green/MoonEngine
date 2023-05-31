package com.greentree.engine.moon.modules;

import com.greentree.engine.moon.modules.property.EnginePropertiesBase;
import com.greentree.engine.moon.modules.property.ExitManagerProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Engine {

    private static final Logger logger = LogManager.getLogger(Engine.class);

    private Engine() {
    }

    public static void launch(String[] args, LaunchModule launchModule, UpdateModule updateModule,
                              TerminateModule terminateModule) {
        var exitManager = new ExitManagerImpl();
        var properties = new EnginePropertiesBase();
        properties.add(new ExitManagerProperty(exitManager));
        logger.info(() -> "Begin Engine.launch");
        launchModule.launch(properties);
        logger.info(() -> "End Engine.launch");
        while (!exitManager.isShouldExit()) {
            logger.info(() -> "Begin Engine.update");
            updateModule.update();
            logger.info(() -> "End Engine.update");
        }
        logger.info(() -> "Begin Engine.terminate");
        terminateModule.terminate();
        logger.info(() -> "End Engine.terminate");
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
