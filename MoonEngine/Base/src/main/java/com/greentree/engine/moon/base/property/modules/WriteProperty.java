package com.greentree.engine.moon.base.property.modules;

import com.greentree.engine.moon.base.component.UseComponent;
import com.greentree.engine.moon.base.component.UseStage;
import com.greentree.engine.moon.kernel.AliasFor;
import com.greentree.engine.moon.modules.property.EngineProperty;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@UseProperty(state = UseStage.WRITE)
@Retention(RUNTIME)
@Target(METHOD)
public @interface WriteProperty {

    @AliasFor(annotation = UseProperty.class, value = "value")
    Class<? extends EngineProperty>[] value();

}
