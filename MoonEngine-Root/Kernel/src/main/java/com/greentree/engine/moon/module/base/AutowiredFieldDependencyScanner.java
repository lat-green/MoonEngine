package com.greentree.engine.moon.module.base;

import java.lang.reflect.Modifier;
import java.util.stream.Stream;

import com.greentree.commons.injector.Dependency;
import com.greentree.commons.injector.DependencyScanner;
import com.greentree.commons.util.classes.ClassUtil;


public class AutowiredFieldDependencyScanner implements DependencyScanner {
	@Override
	public Stream<? extends Dependency> scan(Class<?> cls) {
		return ClassUtil.getAllFields(cls).stream().filter(x -> {
			var mod = x.getModifiers();
			if(Modifier.isStatic(mod))
				return false;
			if(Modifier.isFinal(mod))
				return false;
			return true;
		}).map(x -> new AutowiredFieldDependency(x));
	}
}
