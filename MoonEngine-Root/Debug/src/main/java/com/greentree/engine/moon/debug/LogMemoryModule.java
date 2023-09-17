package com.greentree.engine.moon.debug;

import com.greentree.engine.moon.modules.UpdateModule;

public record LogMemoryModule() implements UpdateModule {

    @Override
    public void update() {
        log();
    }

    public static void log() {
        System.out.println((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024);
    }

}
