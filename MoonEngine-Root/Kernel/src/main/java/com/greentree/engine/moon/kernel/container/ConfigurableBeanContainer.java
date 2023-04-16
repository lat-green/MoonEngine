package com.greentree.engine.moon.kernel.container;

import java.lang.reflect.InvocationTargetException;

import com.greentree.commons.util.classes.ClassUtil;

public interface ConfigurableBeanContainer extends BeanContainer {
	
	
	<T> T removeBean(Class<T> cls);
	
	default Object addBean(Class<?> cls) {
		if(hasBean(cls))
			return getBean(cls).get();
		try {
			return addBean(ClassUtil.newInstance(cls));
		}catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	Object addBean(Object bean);
	
}
