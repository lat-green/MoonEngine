package com.greentree.engine.moon.base.systems;

import java.lang.reflect.Method;
import java.util.stream.Stream;

public enum UseStage {
	CREATE {
		
		@Override
		public Stream<Class<?>> get(Method method) {
			var s3 = Stream.of(method.getAnnotationsByType(Use.class)).filter(x -> x.state() == this)
					.map(Use::value);
			var a1 = method.getAnnotation(CreateComponent.class);
			var a2 = method.getAnnotation(CreateWorldComponent.class);
			Stream<Class<?>> s1 = a1 == null ? Stream.empty() : Stream.of(a1.value());
			Stream<Class<?>> s2 = a2 == null ? Stream.empty() : Stream.of(a2.value());
			return Stream.concat(s3, Stream.concat(s1, s2)).distinct();
		}
	},WRITE {
		
		@Override
		public Stream<Class<?>> get(Method method) {
			var s3 = Stream.of(method.getAnnotationsByType(Use.class)).filter(x -> x.state() == this)
					.map(Use::value);
			var a1 = method.getAnnotation(WriteComponent.class);
			var a2 = method.getAnnotation(WriteWorldComponent.class);
			Stream<Class<?>> s1 = a1 == null ? Stream.empty() : Stream.of(a1.value());
			Stream<Class<?>> s2 = a2 == null ? Stream.empty() : Stream.of(a2.value());
			return Stream.concat(s3, Stream.concat(s1, s2)).distinct();
		}
	},READ {
		
		@Override
		public Stream<Class<?>> get(Method method) {
			var s3 = Stream.of(method.getAnnotationsByType(Use.class)).filter(x -> x.state() == this)
					.map(Use::value);
			var a1 = method.getAnnotation(ReadComponent.class);
			var a2 = method.getAnnotation(ReadWorldComponent.class);
			Stream<Class<?>> s1 = a1 == null ? Stream.empty() : Stream.of(a1.value());
			Stream<Class<?>> s2 = a2 == null ? Stream.empty() : Stream.of(a2.value());
			return Stream.concat(s3, Stream.concat(s1, s2)).distinct();
		}
	},DESTROY {
		
		@Override
		public Stream<Class<?>> get(Method method) {
			var s3 = Stream.of(method.getAnnotationsByType(Use.class)).filter(x -> x.state() == this)
					.map(Use::value);
			var a1 = method.getAnnotation(DestroyComponent.class);
			var a2 = method.getAnnotation(DestroyWorldComponent.class);
			Stream<Class<?>> s1 = a1 == null ? Stream.empty() : Stream.of(a1.value());
			Stream<Class<?>> s2 = a2 == null ? Stream.empty() : Stream.of(a2.value());
			return Stream.concat(s3, Stream.concat(s1, s2)).distinct();
		}
	};
	
	public abstract Stream<Class<?>> get(Method method);
	
	public Stream<Class<?>> get(Object obj, String method) {
		return get(obj.getClass(), method);
	}
	
	public Stream<Class<?>> get(Class<?> cls, String method) {
		return get(getMethod(cls, method));
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
