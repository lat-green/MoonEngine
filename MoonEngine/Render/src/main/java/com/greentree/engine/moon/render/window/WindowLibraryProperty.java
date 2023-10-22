package com.greentree.engine.moon.render.window;

import com.greentree.engine.moon.ecs.scene.SceneProperty;
import com.greentree.engine.moon.modules.property.EngineProperty;

public record WindowLibraryProperty(WindowLibrary library)
        implements EngineProperty, SceneProperty {

}
