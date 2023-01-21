package com.greentree.engine.moon.ecs.system.debug;

import com.greentree.engine.moon.ecs.system.ECSSystem;

public interface MethodSystemsProfiler extends AutoCloseable {

	@Override
	void close();

	StartSystemsProfiler start(ECSSystem s);

}