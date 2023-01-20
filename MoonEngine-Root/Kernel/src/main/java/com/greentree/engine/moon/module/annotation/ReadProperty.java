package com.greentree.engine.moon.module.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.moon.module.EngineProperty;


@Retention(RUNTIME)
@Target(METHOD)
public @interface ReadProperty{
	
	Class<? extends EngineProperty>[] value();
	
}
