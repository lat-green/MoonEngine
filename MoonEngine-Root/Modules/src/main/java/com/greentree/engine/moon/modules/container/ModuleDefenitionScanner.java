package com.greentree.engine.moon.modules.container;

import java.util.stream.Stream;

public interface ModuleDefenitionScanner {
	
	Stream<? extends ModuleDefenition> scan();
	
}
