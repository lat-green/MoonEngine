package com.greentree.engine.moon.render.window;

import com.greentree.engine.moon.base.property.world.ReadSceneProperty;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;

public final class DisableCursor implements InitSystem, DestroySystem {

    private CursorInputMode cursorInputMode;
    private SceneProperties properties;

    @ReadSceneProperty(WindowProperty.class)
    @Override
    public void init(SceneProperties properties) {
        this.properties = properties;
        final var window = properties.get(WindowProperty.class).window();
        this.cursorInputMode = window.getInputMode();
        window.setInputMode(CursorInputMode.DISABLED);
    }

    @ReadSceneProperty(WindowProperty.class)
    @Override
    public void destroy() {
        final var window = properties.get(WindowProperty.class).window();
        window.setInputMode(cursorInputMode);
        cursorInputMode = null;
        properties = null;
    }

}
