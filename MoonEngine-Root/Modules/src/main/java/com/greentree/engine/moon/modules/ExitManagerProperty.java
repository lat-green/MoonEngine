package com.greentree.engine.moon.modules;

import com.greentree.engine.moon.modules.phase.ExitManagerImpl;

public record ExitManagerProperty(ExitManager manager) implements EngineProperty {
	
	
	public ExitManagerProperty() {
		this(new ExitManagerImpl());
	}
	
}
