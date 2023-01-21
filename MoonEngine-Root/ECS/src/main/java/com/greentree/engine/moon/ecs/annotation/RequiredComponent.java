package com.greentree.engine.moon.ecs.annotation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.greentree.engine.moon.ecs.component.Component;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface RequiredComponent {

	Class<? extends Component>[] value();

}
