package com.greentree.engine.moon.kernel.annotation;

import com.greentree.commons.injector.DependencyScanner;
import com.greentree.commons.injector.MergeDependencyScanner;
import com.greentree.engine.moon.kernel.AutowiredFieldDependencyScanner;

@EngineConfiguration
public class DependencyScannerConfig {
	
	@EngineBeanFactory
	public DependencyScanner newDependencyScanner() {
		return new MergeDependencyScanner(new AutowiredFieldDependencyScanner());
	}
	
}
