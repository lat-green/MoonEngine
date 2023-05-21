package com.greentree.engine.moon.base.modules.info;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.Stream;

import com.greentree.engine.moon.base.modules.CreateProperty;
import com.greentree.engine.moon.base.modules.DestroyProperty;
import com.greentree.engine.moon.base.modules.ReadProperty;
import com.greentree.engine.moon.base.modules.WriteProperty;
import com.greentree.engine.moon.modules.EngineModule;


public class AnnotatedCWRDMethodModuleInfo implements CWRDMethodModuleInfo {
	
	private static <A extends Annotation> Optional<A> get(Object a, String method, Class<A> annotationClass) {
		final var m = getMethod(a.getClass(), method);
		if(m == null)
			throw new IllegalArgumentException(a + " " + method + " " + annotationClass);
		return Optional.ofNullable(m.getAnnotation(annotationClass));
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
	public Stream<Class<?>> getCreate(EngineModule module, String method) {
		final var an = get(module, method, CreateProperty.class);
		return an.stream().flatMap(x -> Stream.of(x.value()));
	}
	
	@Override
	public Stream<Class<?>> getWrite(EngineModule module, String method) {
		final var an = get(module, method, WriteProperty.class);
		return an.stream().flatMap(x -> Stream.of(x.value()));
	}
	
	@Override
	public Stream<Class<?>> getRead(EngineModule module, String method) {
		final var an = get(module, method, ReadProperty.class);
		return an.stream().flatMap(x -> Stream.of(x.value()));
	}
	
	@Override
	public Stream<Class<?>> getDestroy(EngineModule module, String method) {
		final var an = get(module, method, DestroyProperty.class);
		return an.stream().flatMap(x -> Stream.of(x.value()));
	}
	
}
