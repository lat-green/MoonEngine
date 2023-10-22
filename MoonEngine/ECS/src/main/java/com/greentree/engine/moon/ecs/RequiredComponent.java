package com.greentree.engine.moon.ecs;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.greentree.engine.moon.ecs.component.Component;
import com.greentree.engine.moon.kernel.AnnotationInherited;

@AnnotationInherited(RequiredComponentClass.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, ANNOTATION_TYPE})
@Repeatable(RequiredComponentRepeatable.class)
public @interface RequiredComponent {

	Class<? extends Component>[] value() default {};

}
