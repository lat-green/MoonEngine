package com.greentree.engine.moon.base.component;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.moon.ecs.component.Component;
import com.greentree.engine.moon.kernel.AliasFor;

@UseComponent(state = UseStage.READ)
@Retention(RUNTIME)
@Target(METHOD)
@Repeatable(ReadComponentRepeatable.class)
public @interface ReadComponent{
	
	@AliasFor(annotation = UseComponent.class, value = "value")
	Class<? extends Component>[] value();
	
}
