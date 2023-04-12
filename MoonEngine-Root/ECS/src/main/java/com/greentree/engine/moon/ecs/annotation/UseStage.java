package com.greentree.engine.moon.ecs.annotation;

import java.lang.reflect.Method;
import java.util.stream.Stream;

public enum UseStage {
	CREATE {
		
		@Override
		Stream<Class<?>> get(Method method) {
			var s1 = Stream.of(method.getAnnotationsByType(Use.class)).filter(x -> x.state() == this)
					.map(x -> x.value());
			var s2 = Stream.of(method.getAnnotation(CreateComponent.class).value());
			return Stream.concat(s1, s2);
		}
	},WRITE {
		
		@Override
		Stream<Class<?>> get(Method method) {
			// TODO Auto-generated method stub
			return null;
		}
	},READ {
		
		@Override
		Stream<Class<?>> get(Method method) {
			// TODO Auto-generated method stub
			return null;
		}
	},DESTROY {
		
		@Override
		Stream<Class<?>> get(Method method) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	abstract Stream<Class<?>> get(Method method);
}
