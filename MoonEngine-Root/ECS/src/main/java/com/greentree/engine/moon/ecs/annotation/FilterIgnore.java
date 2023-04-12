package com.greentree.engine.moon.ecs.annotation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.greentree.engine.moon.ecs.component.Component;


@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
@Repeatable(FilterIgnores.class)
public @interface FilterIgnore {

	Class<? extends Component> value();

}
