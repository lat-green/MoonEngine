package com.greentree.engine.moon.base.property.world;

import com.greentree.engine.moon.base.component.UseComponent;
import com.greentree.engine.moon.base.component.UseStage;
import com.greentree.engine.moon.ecs.scene.SceneProperty;
import com.greentree.engine.moon.kernel.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@UseSceneProperty(state = UseStage.DESTROY)
@Retention(RUNTIME)
@Target(METHOD)
public @interface DestroySceneProperty {

    @AliasFor(annotation = UseComponent.class, value = "value")
    Class<? extends SceneProperty>[] value();

}
