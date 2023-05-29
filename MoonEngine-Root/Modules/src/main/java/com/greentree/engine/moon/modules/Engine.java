package com.greentree.engine.moon.modules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.greentree.engine.moon.modules.property.EnginePropertiesBase;
import com.greentree.engine.moon.modules.property.ExitManagerProperty;

public final class Engine {
	
	private static Logger logger = LogManager.getLogger(Engine.class);
	
	private Engine() {
	}
	
	public static void launch(String[] args, LaunchModule launchModule, UpdateModule updateModule,
			TerminateModule terminateModule) {
		var exitManager = new ExitManagerImpl();
		var properties = new EnginePropertiesBase();
		properties.add(new ExitManagerProperty(exitManager));
		logger.info(() -> "Engine.launch");
		launchModule.launch(properties);
		while(!exitManager.isShouldExit()) {
			logger.info(() -> "Engine.update");
			updateModule.update();
		}
		logger.info(() -> "Engine.terminate");
		terminateModule.terminate();
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
