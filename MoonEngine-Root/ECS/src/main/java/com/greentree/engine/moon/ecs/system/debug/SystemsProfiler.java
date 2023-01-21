package com.greentree.engine.moon.ecs.system.debug;

import java.io.Serializable;


public interface SystemsProfiler extends Serializable {

	MethodSystemsProfiler init();
	MethodSystemsProfiler destroy();
	MethodSystemsProfiler update();

}
