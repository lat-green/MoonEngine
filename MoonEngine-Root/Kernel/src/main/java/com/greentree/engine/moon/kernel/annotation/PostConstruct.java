package com.greentree.engine.moon.kernel.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.moon.kernel.processor.PostConstructProcessor;

@AnnotationInherited
@Import({PostConstructProcessor.class})
@Retention(RUNTIME)
@Target({METHOD, ANNOTATION_TYPE})
public @interface PostConstruct{
	
}
