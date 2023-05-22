package com.greentree.engine.moon.base.component;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import com.greentree.engine.moon.base.property.world.CreateWorldComponent;
import com.greentree.engine.moon.base.property.world.DestroyWorldComponent;
import com.greentree.engine.moon.base.property.world.ReadWorldComponent;
import com.greentree.engine.moon.base.property.world.UseWorldComponent;
import com.greentree.engine.moon.base.property.world.WriteWorldComponent;
import com.greentree.engine.moon.ecs.WorldComponent;
import com.greentree.engine.moon.ecs.component.Component;
import com.greentree.engine.moon.kernel.AnnotationUtil;

public enum UseStage {
	CREATE {
		
		@Override
		public Stream<Class<? extends WorldComponent>> getWorldComponent(Method method) {
			var s1 = AnnotationUtil.getAnnotations(method, UseWorldComponent.class).filter(x -> x.state() == this)
					.map(x -> x.value());
			var s2 = AnnotationUtil.getAnnotations(method, CreateWorldComponent.class)
					.flatMap(x -> Stream.of(x.value()));
			return Stream.concat(s1, s2).distinct();
		}
	},WRITE {
		
		@Override
		public Stream<Class<? extends WorldComponent>> getWorldComponent(Method method) {
			var s1 = AnnotationUtil.getAnnotations(method, UseWorldComponent.class).filter(x -> x.state() == this)
					.map(x -> x.value());
			var s2 = AnnotationUtil.getAnnotations(method, WriteWorldComponent.class)
					.flatMap(x -> Stream.of(x.value()));
			return Stream.concat(s1, s2).distinct();
		}
	},READ {
		
		@Override
		public Stream<Class<? extends WorldComponent>> getWorldComponent(Method method) {
			var s1 = AnnotationUtil.getAnnotations(method, UseWorldComponent.class).filter(x -> x.state() == this)
					.map(x -> x.value());
			var s2 = AnnotationUtil.getAnnotations(method, ReadWorldComponent.class).flatMap(x -> Stream.of(x.value()));
			return Stream.concat(s1, s2).distinct();
		}
	},DESTROY {
		
		@Override
		public Stream<Class<? extends WorldComponent>> getWorldComponent(Method method) {
			var s1 = AnnotationUtil.getAnnotations(method, UseWorldComponent.class).filter(x -> x.state() == this)
					.map(x -> x.value());
			var s2 = AnnotationUtil.getAnnotations(method, DestroyWorldComponent.class)
					.flatMap(x -> Stream.of(x.value()));
			return Stream.concat(s1, s2).distinct();
		}
	};
	
	public Stream<Class<? extends Component>> getComponent(Method method) {
		return AnnotationUtil.getAnnotations(method, UseComponent.class).filter(x -> x.state() == this)
				.flatMap(x -> Stream.of(x.value()));
	}
	
	public Stream<Class<? extends WorldComponent>> getWorldComponent(Method method) {
		return AnnotationUtil.getAnnotations(method, UseWorldComponent.class).filter(x -> x.state() == this)
				.map(x -> x.value());
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
