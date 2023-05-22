package com.greentree.engine.moon.base.property.modules;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.moon.modules.property.EngineProperty;


@Retention(RUNTIME)
@Target(METHOD)
public @interface ReadProperty{
	
	Class<? extends EngineProperty>[] value();
	
}
