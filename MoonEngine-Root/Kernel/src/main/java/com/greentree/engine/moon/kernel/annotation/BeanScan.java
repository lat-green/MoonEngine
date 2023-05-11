package com.greentree.engine.moon.kernel.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.ComponentScan;


@Retention(RUNTIME)
@Target(TYPE)
@EngineBean
@ComponentScan
public @interface BeanScan{
	
}
