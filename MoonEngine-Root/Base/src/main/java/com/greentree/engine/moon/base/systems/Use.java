package com.greentree.engine.moon.base.systems;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Retention(RUNTIME)
@Target(METHOD)
@Repeatable(UseRepeatable.class)
public @interface Use{

	Class<?> value();
	
	UseStage state();
	
}
