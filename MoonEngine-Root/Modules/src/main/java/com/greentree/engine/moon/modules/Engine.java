package com.greentree.engine.moon.modules;

import com.greentree.engine.moon.modules.property.EnginePropertiesBase;
import com.greentree.engine.moon.modules.property.ExitManagerProperty;

public final class Engine {
	private Engine() {
	}
	
	public static void launch(String[] args, LaunchModule launchModule, UpdateModule updateModule,
			TerminateModule terminateModule) {
		var exitManager = new ExitManagerImpl();
		var properties = new EnginePropertiesBase();
		properties.add(new ExitManagerProperty(exitManager));
		
		launchModule.launch(properties);
		while(!exitManager.isShouldExit())
			updateModule.update();
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
