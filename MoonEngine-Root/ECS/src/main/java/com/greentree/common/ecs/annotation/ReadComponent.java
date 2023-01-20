package com.greentree.common.ecs.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.common.ecs.component.Component;


@Retention(RUNTIME)
@Target(METHOD)
public @interface ReadComponent{
	
	Class<? extends Component>[] value();
	
}
