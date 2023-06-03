package com.greentree.engine.moon.base.property.world;

import com.greentree.engine.moon.base.component.UseStage;
import com.greentree.engine.moon.ecs.scene.SceneProperty;

import java.util.Map;

public record UseScenePropertyClass(Class<? extends SceneProperty>[] value, UseStage state)
        implements UseSceneProperty {

    @SuppressWarnings("unchecked")
    public UseScenePropertyClass(Map<String, Object> values) {
        this((Class<? extends SceneProperty>[]) values.get("value"), (UseStage) values.get("state"));
    }

    @Override
    public Class<? extends UseSceneProperty> annotationType() {
        return UseSceneProperty.class;
    }

}
