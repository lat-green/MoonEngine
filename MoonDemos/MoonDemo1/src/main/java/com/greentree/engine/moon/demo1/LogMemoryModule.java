package com.greentree.engine.moon.demo1;

import com.greentree.engine.moon.modules.UpdateModule;


public record LogMemoryModule() implements UpdateModule {
	
	public static void log() {
		System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024);
	}
	
	@Override
	public void update() {
		log();
	}
	
}
