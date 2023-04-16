package com.greentree.engine.moon.modules.base;

import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.modules.UpdateModule;

@Deprecated
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
