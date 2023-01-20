package com.greentree.common.ecs.system.debug;


public interface StartSystemsProfiler extends AutoCloseable {

	@Override
	void close();
	
}