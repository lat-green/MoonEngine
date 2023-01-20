package com.greentree.common.ecs.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.common.ecs.system.ECSSystem;

/** This annotation should be marked WorldComponents in order to indicate the
 * system that creates this WorldComponent.
 * @author Latyshev Arseny */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface CreateSystem {
	
	Class<? extends ECSSystem> value();
	
}
