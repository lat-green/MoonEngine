package com.greentree.engine.moon.bean.container;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import com.greentree.commons.graph.DirectedGraph;
import com.greentree.commons.injector.DependencyScanner;
import com.greentree.commons.injector.InjectionContainer;
import com.greentree.commons.injector.Injector;
import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.engine.moon.bean.EngineBeanProcessor;
import com.greentree.engine.moon.bean.annotation.ImportClassBeanFactoryProcessor;
import com.greentree.engine.moon.bean.definition.BeanDefinition;


public class BeanFactoryBase implements BeanFactory {
	
	private static void inject(BeanContainer ctx, Object obj) {
		var SCANNER = ctx.getBean(DependencyScanner.class, AutowiredFieldDependencyScanner::new);
		var INJECTION_CONTAINER = ctx.getBean(InjectionContainer.class, () -> new InjectionContainer() {
			
			@SuppressWarnings("unchecked")
			@Override
			public <T> Optional<T> get(Class<T> cls) {
				if(cls.isInstance(ctx))
					return Optional.of((T) ctx);
				return ctx.getBean(cls);
			}
		});
		var injector = new Injector(INJECTION_CONTAINER, SCANNER);
		injector.inject(obj);
	}
	
	private static <T> T newInstance(Class<T> cls) {
		try {
			return cls.getConstructor().newInstance();
		}catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public BeanContainer newContainer(Stream<BeanDefinition> definitions) {
		var graph = new DirectedGraph<Class<?>>();
		
		
		
		graph.add(ImportClassBeanFactoryProcessor.class);
		definitions.forEach(x -> {
			graph.add(x.type());
			x.properties().forEach(y -> {
				graph.add(x.type(), y);
			});
		});
		var list = graph.getTopologicalSort();
		
		list.sort(Comparator.comparing(x -> ClassUtil.isExtends(ImportClassBeanFactoryProcessor.class, x) ? 0 : 1));
		list.sort(Comparator.comparing(x -> ClassUtil.isExtends(EngineBeanProcessor.class, x) ? 0 : 1));
		var ctx = new BeanContainerBuilderBase();
		for(var cls : list) {
			var processors = processors(ctx).toList();
			var inst = newInstance(cls);
			for(var p : processors)
				inst = p.processBeforeInitialization(inst);
			inject(ctx, inst);
			for(var p : processors)
				inst = p.processAfterInitialization(inst);
			ctx.addBean(inst);
		}
		return ctx.build();
	}
	
	private Stream<? extends EngineBeanProcessor> processors(BeanContainer ctx) {
		return ctx.getBeans(EngineBeanProcessor.class);
	}
}
