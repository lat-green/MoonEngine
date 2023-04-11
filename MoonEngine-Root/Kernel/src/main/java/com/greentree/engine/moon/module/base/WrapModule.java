package com.greentree.engine.moon.module.base;

import com.greentree.engine.moon.module.EngineModule;
import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.TerminateModule;
import com.greentree.engine.moon.module.UpdateModule;

public class WrapModule implements LaunchModule, UpdateModule, TerminateModule {
	
	private final EngineModule module;
	
	public WrapModule(EngineModule module) {
		this.module = module;
	}
	
	@Override
	public void terminate() {
		if(module instanceof TerminateModule tm)
			tm.terminate();
	}
	
	@Override
	public void update() {
		if(module instanceof UpdateModule um)
			um.update();
	}
	
	@Override
	public void launch(EngineProperties properties) {
		if(module instanceof LaunchModule lm)
			lm.launch(properties);
	}
	
}
