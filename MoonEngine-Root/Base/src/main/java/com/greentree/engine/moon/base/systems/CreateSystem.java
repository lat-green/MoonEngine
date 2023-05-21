package com.greentree.engine.moon.base.systems;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.moon.ecs.system.ECSSystem;

/** This annotation should be marked WorldComponents in order to indicate the
 * system that creates this WorldComponent.
 * @author Latyshev Arseny */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface CreateSystem {
	
	Class<? extends ECSSystem> value();
	
}
