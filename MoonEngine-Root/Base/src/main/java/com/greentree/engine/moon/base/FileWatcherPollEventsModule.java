package com.greentree.engine.moon.base;

import com.greentree.commons.data.FileWatcher;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class FileWatcherPollEventsModule implements UpdateSystem {
	
	@Override
	public void update() {
		FileWatcher.pollEvents();
	}
	
}
