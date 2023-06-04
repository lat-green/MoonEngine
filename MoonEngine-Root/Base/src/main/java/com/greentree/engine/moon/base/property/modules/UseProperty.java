package com.greentree.engine.moon.base.property.modules;

import com.greentree.engine.moon.base.component.UseStage;
import com.greentree.engine.moon.ecs.scene.SceneProperty;
import com.greentree.engine.moon.kernel.AnnotationInherited;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@AnnotationInherited(UsePropertyClass.class)
@Retention(RUNTIME)
@Target({METHOD, ANNOTATION_TYPE})
@Repeatable(UsePropertyRepeatable.class)
public @interface UseProperty {

    Class<? extends SceneProperty>[] value() default {};

    UseStage state();

}
