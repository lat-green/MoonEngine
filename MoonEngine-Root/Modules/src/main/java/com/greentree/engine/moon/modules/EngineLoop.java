package com.greentree.engine.moon.modules;

import com.greentree.engine.moon.modules.container.ModuleContainer;

public class EngineLoop implements ExitManager {
	
	private boolean runnable = false;
	private final EngineProperties properties;
	private final ModuleContainer container;
	
	public EngineLoop(ModuleContainer container) {
		this(container, new EnginePropertiesBase());
	}
	
	public EngineLoop(ModuleContainer container, EngineProperties properties) {
		this.container = container;
		this.properties = properties;
		properties.add(new ExitManagerProperty(this));
	}
	
	@Override
	public void exit() {
		runnable = false;
	}
	
	public void run() {
		if(runnable)
			throw new IllegalArgumentException("SceneManagerBase already run");
		runnable = true;
		
		container.launch(properties);
		
		while(runnable)
			container.update();
			
		container.terminate();
	}
	
	public EngineProperties properties() {
		return properties;
	}
	
	public ModuleContainer container() {
		return container;
	}
	
}
