package com.greentree.engine.moon.module.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.moon.module.EngineProperty;


@Retention(RUNTIME)
@Target(METHOD)
public @interface WriteProperty{
	
	Class<? extends EngineProperty>[] value();
	
}
