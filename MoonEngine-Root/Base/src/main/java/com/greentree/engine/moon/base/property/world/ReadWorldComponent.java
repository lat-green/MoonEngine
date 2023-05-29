package com.greentree.engine.moon.base.property.world;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.greentree.engine.moon.base.component.UseComponent;
import com.greentree.engine.moon.base.component.UseStage;
import com.greentree.engine.moon.ecs.WorldComponent;
import com.greentree.engine.moon.kernel.AliasFor;


@UseWorldComponent(state = UseStage.READ)
@Retention(RUNTIME)
@Target(METHOD)
public @interface ReadWorldComponent{
	
	@AliasFor(annotation = UseComponent.class, value = "value")
	Class<? extends WorldComponent>[] value();
	
}
