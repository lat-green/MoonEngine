package com.greentree.common.ecs.system.debug;

import java.io.Serializable;


public interface SystemsProfiler extends Serializable {

	MethodSystemsProfiler init();
	MethodSystemsProfiler destroy();
	MethodSystemsProfiler update();

}
