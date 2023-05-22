package com.greentree.engine.moon.base.property.modules.info;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import com.greentree.engine.moon.base.info.CWRDMethodInfo;
import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.base.property.modules.DestroyProperty;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.kernel.AnnotationUtil;


public class AnnotatedCWRDMethodPropertyInfo implements CWRDMethodInfo {
	
	private static <A extends Annotation> Stream<A> get(Object object, String methodName, Class<A> annotationClass) {
		final var method = getMethod(object.getClass(), methodName);
		if(method == null)
			throw new IllegalArgumentException(object + " " + method + " " + annotationClass);
		return AnnotationUtil.getAnnotations(method, annotationClass);
	}
	
	private static Method getMethod(Class<?> cls, String method) {
		for(var m : cls.getMethods())
			if(m.getName().equals(method))
				return m;
		if(cls != Object.class)
			return getMethod(cls.getSuperclass(), method);
		return null;
	}
	
	@Override
	public Stream<Class<?>> getCreate(Object object, String method) {
		final var an = get(object, method, CreateProperty.class);
		return an.flatMap(x -> Stream.of(x.value()));
	}
	
	@Override
	public Stream<Class<?>> getWrite(Object object, String method) {
		final var an = get(object, method, WriteProperty.class);
		return an.flatMap(x -> Stream.of(x.value()));
	}
	
	@Override
	public Stream<Class<?>> getRead(Object object, String method) {
		final var an = get(object, method, ReadProperty.class);
		return an.flatMap(x -> Stream.of(x.value()));
	}
	
	@Override
	public Stream<Class<?>> getDestroy(Object object, String method) {
		final var an = get(object, method, DestroyProperty.class);
		return an.flatMap(x -> Stream.of(x.value()));
	}
	
}
