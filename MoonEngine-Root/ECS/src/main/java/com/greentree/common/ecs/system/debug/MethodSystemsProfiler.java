package com.greentree.common.ecs.system.debug;

import com.greentree.common.ecs.system.ECSSystem;

public interface MethodSystemsProfiler extends AutoCloseable {

	@Override
	void close();

	StartSystemsProfiler start(ECSSystem s);

}