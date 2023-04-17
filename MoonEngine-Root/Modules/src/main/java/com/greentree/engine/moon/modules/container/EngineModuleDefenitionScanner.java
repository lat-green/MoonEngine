package com.greentree.engine.moon.modules.container;

import java.util.stream.Stream;

public interface EngineModuleDefenitionScanner {
	
	Stream<? extends EngineModuleDefenition> scan();
	
}
