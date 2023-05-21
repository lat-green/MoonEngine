package com.greentree.engine.moon.ecs.component;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Retention(SOURCE)
@Target(TYPE)
public @interface ComponentPool{
	
	Class<? extends AbstractComponentPool<?>> value();
	
}
