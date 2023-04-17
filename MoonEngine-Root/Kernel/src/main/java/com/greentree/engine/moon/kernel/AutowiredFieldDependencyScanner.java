package com.greentree.engine.moon.kernel;

import java.lang.reflect.Modifier;
import java.util.stream.Stream;

import com.greentree.commons.injector.DependencyScanner;
import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.engine.moon.kernel.annotation.AnnotationUtil;
import com.greentree.engine.moon.kernel.annotation.Autowired;


public class AutowiredFieldDependencyScanner implements DependencyScanner {
	@Override
	public Stream<? extends AutowiredFieldDependency> scan(Class<?> cls) {
		return ClassUtil.getAllFields(cls).stream().filter(x -> {
			var mod = x.getModifiers();
			if(Modifier.isStatic(mod))
				return false;
			if(Modifier.isFinal(mod))
				return false;
			if(!AnnotationUtil.hasAnnotation(x, Autowired.class))
				return false;
			return true;
		}).map(x -> new AutowiredFieldDependency(x));
	}
}
