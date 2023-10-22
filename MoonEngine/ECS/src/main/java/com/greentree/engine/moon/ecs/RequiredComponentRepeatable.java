package com.greentree.engine.moon.ecs;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Documented
@Retention(RUNTIME)
@Target({TYPE, ANNOTATION_TYPE})
public @interface RequiredComponentRepeatable{
	
	RequiredComponent[] value();
	
}