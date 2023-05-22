package com.greentree.engine.moon.base.component;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Retention(RUNTIME)
@Target({METHOD, ANNOTATION_TYPE})
public @interface UseComponentRepeatable {

	UseComponent[] value();
	
}
