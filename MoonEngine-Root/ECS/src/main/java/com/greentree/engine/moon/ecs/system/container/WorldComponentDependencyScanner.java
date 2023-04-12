package com.greentree.engine.moon.ecs.system.container;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.greentree.commons.injector.Dependency;
import com.greentree.commons.injector.DependencyScanner;
import com.greentree.commons.injector.FieldDependency;
import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.engine.moon.ecs.WorldComponent;
import com.greentree.engine.moon.ecs.annotation.AnnotationUtil;
import com.greentree.engine.moon.ecs.annotation.Autowired;


public class WorldComponentDependencyScanner implements DependencyScanner {
	
	@Override
	public Stream<? extends Dependency> scan(Object object) {
		var cls = object.getClass();
		
		var init_created = newSet(AnnotationUtil.getWorldCreate(object, AnnotationUtil.INIT));
		return ClassUtil.getAllNotStaticFields(cls).stream().filter(x -> x.isAnnotationPresent(Autowired.class))
				.filter(x -> ClassUtil.isExtends(WorldComponent.class, x.getType()))
				.filter(x -> !init_created.contains(x.getType()))
				.map(x -> new FieldDependency(object, x));
	}
	
	private static <T> Set<T> newSet(Iterable<? extends T> initCreate) {
		var result = new HashSet<T>();
		for(var x : initCreate)
			result.add(x);
		return result;
	}
	
}
