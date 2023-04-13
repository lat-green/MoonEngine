package com.greentree.engine.moon;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ServiceLoader;

import com.greentree.engine.moon.bean.annotation.ImportClassBeanFactoryProcessor;
import com.greentree.engine.moon.bean.container.BeanContainerBuilderBase;
import com.greentree.engine.moon.module.EngineModule;
import com.greentree.engine.moon.module.base.EngineLoop;

public final class Engine {
	
	private Engine() {
	}
	
	private static Class<?> getCaller() {
		try {
			return Class.forName(Thread.currentThread().getStackTrace()[3].getClassName());
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("unreal exception", e);
		}
	}
	
	public static void launch(String[] args, EngineModule... modules) {
		System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
		System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
		
		var cls = getCaller();
		
		var context = new BeanContainerBuilderBase();
		
		context.addBean(ImportClassBeanFactoryProcessor.class);
		context.addBean(cls);
		for(var m : modules)
			context.addBean(m);
		final var autoAddModules = ServiceLoader.load(EngineModule.class);
		for(var m : autoAddModules)
			context.addBean(m);
		
		var allModules = context.getBeans(EngineModule.class).toList();
		
		final var scenes = new EngineLoop(allModules);
		scenes.run();
	}
	
}
