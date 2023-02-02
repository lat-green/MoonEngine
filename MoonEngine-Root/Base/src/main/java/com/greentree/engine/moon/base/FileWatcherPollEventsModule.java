package com.greentree.engine.moon.base;

import com.greentree.commons.data.FileWatcher;
import com.greentree.engine.moon.module.UpdateModule;

public class FileWatcherPollEventsModule implements UpdateModule {
	
	@Override
	public void update() {
		FileWatcher.pollEvents();
	}
	
}
