package com.greentree.engine.moon.ecs.component;



import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeUtil;

public interface AbstractComponentPool<C extends Component> {
	
	default TypeInfo<C> getType() {
		return TypeUtil.getFirstAtgument(getClass(), AbstractComponentPool.class);
	}
	
	C newComponent(ConstructorParameters parameters);
	
}
