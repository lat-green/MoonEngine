package com.greentree.engine.moon.render.window;

import com.greentree.engine.moon.ecs.scene.SceneProperty;
import com.greentree.engine.moon.modules.property.EngineProperty;

import java.util.Objects;

public record WindowProperty(Window window) implements EngineProperty, SceneProperty {

    public WindowProperty {
        Objects.requireNonNull(window);
    }

}
