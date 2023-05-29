package com.greentree.engine.moon.base.component;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import com.greentree.engine.moon.base.property.world.UseWorldComponent;
import com.greentree.engine.moon.ecs.WorldComponent;
import com.greentree.engine.moon.ecs.component.Component;
import com.greentree.engine.moon.kernel.AnnotationUtil;

public enum UseStage {
	
	CREATE,WRITE,READ,DESTROY;
	
	public Stream<Class<? extends Component>> getComponent(Method method) {
		return AnnotationUtil.getAnnotations(method, UseComponent.class).filter(x -> x.state() == this)
				.flatMap(x -> Stream.of(x.value()));
	}
	
	public Stream<Class<? extends WorldComponent>> getWorldComponent(Method method) {
		return AnnotationUtil.getAnnotations(method, UseWorldComponent.class).filter(x -> x.state() == this)
				.flatMap(x -> Stream.of(x.value()));
	}
	
	public Stream<Class<? extends Component>> getComponent(Object obj, String method) {
		return getComponent(obj.getClass(), method);
	}
	
	public Stream<Class<? extends Component>> getComponent(Class<?> cls, String method) {
		return getComponent(getMethod(cls, method));
	}
	
	public Stream<Class<? extends WorldComponent>> getWorldComponent(Object obj, String method) {
		return getWorldComponent(obj.getClass(), method);
	}
	
	public Stream<Class<? extends WorldComponent>> getWorldComponent(Class<?> cls, String method) {
		return getWorldComponent(getMethod(cls, method));
	}
	
	private static Method getMethod(Class<?> cls, String method) {
		for(var m : cls.getMethods())
			if(m.getName().equals(method))
				return m;
		if(cls != Object.class)
			return getMethod(cls.getSuperclass(), method);
		return null;
	}
}
