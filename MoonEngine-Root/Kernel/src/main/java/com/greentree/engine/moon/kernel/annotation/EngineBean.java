package com.greentree.engine.moon.kernel.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({TYPE, ANNOTATION_TYPE})
@AnnotationInherited
public @interface EngineBean{
	
}
