package com.greentree.engine.moon.module;

public sealed interface EngineModule permits PreLaunchModule, LaunchModule, TerminateModule, UpdateModule {
	
}
