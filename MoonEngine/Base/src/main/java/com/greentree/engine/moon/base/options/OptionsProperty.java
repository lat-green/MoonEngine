package com.greentree.engine.moon.base.options;

import com.greentree.engine.moon.modules.property.EngineProperty;

public record OptionsProperty(OptionsProvider provider) implements EngineProperty, OptionsProvider {

    @Override
    public String get(String name) {
        return provider.get(name);
    }

}