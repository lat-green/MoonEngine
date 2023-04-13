package com.greentree.engine.moon.bean.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Retention(RUNTIME)
@Target({TYPE, ANNOTATION_TYPE})
@EngineBean
@Import(EngineConfigurationProcessor.class)
@AnnotationInherited
public @interface EngineConfiguration{
	
}
