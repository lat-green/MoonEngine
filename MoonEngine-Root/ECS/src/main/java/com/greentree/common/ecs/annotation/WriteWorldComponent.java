package com.greentree.common.ecs.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.common.ecs.WorldComponent;


@Retention(RUNTIME)
@Target(METHOD)
public @interface WriteWorldComponent{
	
	Class<? extends WorldComponent>[] value();
	
}
