package com.greentree.engine.moon.modules.scanner;

import java.util.stream.Stream;

public interface ModuleDefenitionScanner {
	
	Stream<? extends ModuleDefenition> scan();
	
}
