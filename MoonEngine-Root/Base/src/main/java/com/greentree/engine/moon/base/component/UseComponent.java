package com.greentree.engine.moon.base.component;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.moon.ecs.component.Component;
import com.greentree.engine.moon.kernel.AnnotationInherited;

@AnnotationInherited
@Retention(RUNTIME)
@Target({METHOD, ANNOTATION_TYPE})
@Repeatable(UseComponentRepeatable.class)
public @interface UseComponent{

	Class<? extends Component>[] value() default Component.class;
	
	UseStage state();
	
}
