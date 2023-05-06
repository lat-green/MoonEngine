package com.greentree.engine.moon.kernel.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.moon.kernel.processor.PackageScanEngineBeanProcessor;


@Retention(RUNTIME)
@Target(TYPE)
@Import(PackageScanEngineBeanProcessor.class)
@EngineBean
@AnnotationInherited
public @interface BeanScan{
	
}
