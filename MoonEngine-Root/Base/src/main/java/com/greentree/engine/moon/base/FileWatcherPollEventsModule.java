package com.greentree.engine.moon.base;

import com.greentree.commons.data.FileWatcher;
import com.greentree.engine.moon.modules.UpdateModule;

public class FileWatcherPollEventsModule implements UpdateModule {

    @Override
    public void update() {
        FileWatcher.pollEvents();
    }

}
