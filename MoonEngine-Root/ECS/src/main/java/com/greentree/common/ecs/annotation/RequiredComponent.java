package com.greentree.common.ecs.annotation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.greentree.common.ecs.component.Component;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface RequiredComponent {

	Class<? extends Component>[] value();

}
