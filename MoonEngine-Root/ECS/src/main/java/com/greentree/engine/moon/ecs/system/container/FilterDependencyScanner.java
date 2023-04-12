package com.greentree.engine.moon.ecs.system.container;

import java.util.stream.Stream;

import com.greentree.commons.injector.Dependency;
import com.greentree.commons.injector.DependencyScanner;
import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.Autowired;
import com.greentree.engine.moon.ecs.annotation.FilterIgnore;
import com.greentree.engine.moon.ecs.annotation.FilterIgnores;
import com.greentree.engine.moon.ecs.annotation.FilterRequired;
import com.greentree.engine.moon.ecs.annotation.FilterRequireds;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;


public class FilterDependencyScanner implements DependencyScanner {
	
	@Override
	public Stream<? extends Dependency> scan(Object object) {
		var cls = object.getClass();
		return ClassUtil.getAllNotStaticFields(cls).stream()
				.filter(x -> ClassUtil.isExtends(Filter.class, x.getType()))
				.filter(x -> x.isAnnotationPresent(Autowired.class) || x.isAnnotationPresent(FilterRequired.class)
						|| x.isAnnotationPresent(FilterRequireds.class) || x.isAnnotationPresent(FilterIgnore.class)
						|| x.isAnnotationPresent(FilterIgnores.class))
				.map(x ->
				{
					var builder = new FilterBuilder();
					for(var c : x.getAnnotationsByType(FilterRequired.class))
						builder.required(c.value());
					for(var c : x.getAnnotationsByType(FilterIgnore.class))
						builder.required(c.value());
					return (Dependency) container -> {
						var world = container.get(World.class);
						var filter = builder.build(world);
						try {
							ClassUtil.setField(object, x, filter);
						}catch(IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
					};
				});
	}
	
}
