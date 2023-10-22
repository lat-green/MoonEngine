package com.greentree.engine.moon.base.modules.scanner;

import java.util.stream.Stream;

public interface ModuleDefenitionScanner {
	
	Stream<? extends ModuleDefenition> scan();
	
}
