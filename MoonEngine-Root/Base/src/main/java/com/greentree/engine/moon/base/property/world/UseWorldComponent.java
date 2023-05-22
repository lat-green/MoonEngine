package com.greentree.engine.moon.base.property.world;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.moon.base.component.UseStage;
import com.greentree.engine.moon.ecs.WorldComponent;
import com.greentree.engine.moon.kernel.AnnotationInherited;

@AnnotationInherited
@Retention(RUNTIME)
@Target({METHOD, ANNOTATION_TYPE})
@Repeatable(UseWorldComponentRepeatable.class)
public @interface UseWorldComponent{

	Class<? extends WorldComponent> value();
	
	UseStage state();
	
}
