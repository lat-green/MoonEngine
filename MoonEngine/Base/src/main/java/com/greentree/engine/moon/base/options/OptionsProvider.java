package com.greentree.engine.moon.base.options;

public interface OptionsProvider {

    default float getFloat(String name) {
        return Float.parseFloat(get(name));
    }

    String get(String name);

    default int getInt(String name) {
        return Integer.parseInt(get(name));
    }

}
