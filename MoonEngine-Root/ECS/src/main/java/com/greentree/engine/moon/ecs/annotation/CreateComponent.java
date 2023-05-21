package com.greentree.engine.moon.ecs.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.moon.ecs.component.Component;

@Retention(RUNTIME)
@Target(METHOD)
public @interface CreateComponent{
	
	Class<? extends Component>[] value();
	
}
