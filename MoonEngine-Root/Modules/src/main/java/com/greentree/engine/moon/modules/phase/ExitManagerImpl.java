package com.greentree.engine.moon.modules.phase;

import com.greentree.engine.moon.modules.ExitManager;

public class ExitManagerImpl implements ExitManager {
	
	private boolean shouldExit = false;
	
	@Override
	public void exit() {
		shouldExit = true;
	}
	
	@Override
	public boolean isShouldExit() {
		return shouldExit;
	}
	
}
