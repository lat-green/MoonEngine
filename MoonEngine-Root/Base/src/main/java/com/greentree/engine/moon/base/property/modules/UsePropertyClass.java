package com.greentree.engine.moon.base.property.modules;

import com.greentree.engine.moon.base.component.UseStage;
import com.greentree.engine.moon.ecs.scene.SceneProperty;

import java.lang.annotation.Annotation;
import java.util.Map;

public record UsePropertyClass(Class<? extends SceneProperty>[] value, UseStage state) implements UseProperty {

    public UsePropertyClass(Map<String, Object> values) {
        this((Class<? extends SceneProperty>[]) values.get("value"), (UseStage) values.get("state"));
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return UseProperty.class;
    }

}
