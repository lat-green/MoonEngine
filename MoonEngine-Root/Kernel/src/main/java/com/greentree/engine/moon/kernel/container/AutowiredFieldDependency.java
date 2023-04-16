package com.greentree.engine.moon.kernel.container;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.Optional;

import com.greentree.commons.injector.Dependency;
import com.greentree.commons.injector.InjectionContainer;
import com.greentree.engine.moon.kernel.AnnotationUtil;
import com.greentree.engine.moon.kernel.annotation.Autowired;

public record AutowiredFieldDependency(Field field) implements Dependency {
	
	public AutowiredFieldDependency {
		Objects.requireNonNull(field);
		var mod = field.getModifiers();
		if(Modifier.isStatic(mod))
			throw new IllegalArgumentException("static field");
		if(Modifier.isFinal(mod))
			throw new IllegalArgumentException("final field");
	}
	
	@Override
	public String toString() {
		return "AutowiredFieldDependency [" + field + "]";
	}
	
	@Override
	public void set(Object host, InjectionContainer container) {
		try {
			var value = value(container);
			if(value.isEmpty()) {
				var a = AnnotationUtil.getAnnotation(field, Autowired.class);
				var required = a != null ? a.required() : false;
				if(required)
					throw new IllegalArgumentException("not found value to " + field + " to host:" + host);
			}else {
				var access = field.canAccess(host);
				try {
					field.setAccessible(true);
					field.set(host, value.get());
				}finally {
					field.setAccessible(access);
				}
			}
		}catch(IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Optional<?> value(InjectionContainer container) {
		var value = container.get(field.getName(), field.getType());
		if(value.isPresent())
			return value;
		return container.get(field.getType());
	}
	
}
