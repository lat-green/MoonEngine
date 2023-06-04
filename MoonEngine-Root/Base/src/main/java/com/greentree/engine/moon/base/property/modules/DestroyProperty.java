package com.greentree.engine.moon.base.property.modules;

import com.greentree.engine.moon.base.component.UseStage;
import com.greentree.engine.moon.modules.property.EngineProperty;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@UseProperty(state = UseStage.DESTROY)
@Retention(RUNTIME)
@Target(METHOD)
public @interface DestroyProperty {

    Class<? extends EngineProperty>[] value();

}
