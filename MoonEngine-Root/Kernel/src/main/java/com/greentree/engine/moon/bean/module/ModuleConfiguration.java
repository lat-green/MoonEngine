package com.greentree.engine.moon.bean.module;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.moon.bean.annotation.AnnotationInherited;
import com.greentree.engine.moon.bean.annotation.EngineBean;
import com.greentree.engine.moon.bean.annotation.Import;


@Retention(RUNTIME)
@Target({TYPE, ANNOTATION_TYPE})
@EngineBean
@Import({EngineContextEngineProperties.class, LaunchModuleProcessor.class, UpdateModuleProcessor.class,
		TerminateModuleProcessor.class})
@AnnotationInherited
public @interface ModuleConfiguration{
	
}
