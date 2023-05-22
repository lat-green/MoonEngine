package com.greentree.engine.moon.kernel;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
@Documented
public @interface AliasFor {
	
    String value() default "";

    Class<? extends Annotation> annotation() default Annotation.class;
}
