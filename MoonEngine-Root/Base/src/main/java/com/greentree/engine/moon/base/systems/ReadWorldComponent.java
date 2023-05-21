package com.greentree.engine.moon.base.systems;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.moon.ecs.WorldComponent;


@Retention(RUNTIME)
@Target(METHOD)
public @interface ReadWorldComponent{
	
	Class<? extends WorldComponent>[] value();
	
}
