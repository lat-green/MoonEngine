package com.greentree.engine.moon.ecs.system.debug;


public interface StartSystemsProfiler extends AutoCloseable {

	@Override
	void close();
	
}