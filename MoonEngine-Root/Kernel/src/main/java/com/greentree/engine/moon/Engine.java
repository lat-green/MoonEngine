package com.greentree.engine.moon;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import com.greentree.engine.moon.bean.ClassPathBeanScanner;
import com.greentree.engine.moon.bean.container.ConfigurableBeanContainerBase;
import com.greentree.engine.moon.module.EngineModule;
import com.greentree.engine.moon.module.EngineProperties;
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
	
	public static void launch(String[] args) {
		System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
		System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
		
		var cls = getCaller();
		
		var context = new ConfigurableBeanContainerBase();
		var sc = (ClassPathBeanScanner) context.addBean(ClassPathBeanScanner.class);
		sc.scan(context, Engine.class.getPackageName());
		context.addBean(cls);
		
		var allModules = context.getBeans(EngineModule.class).toList();
		
		final var scenes = new EngineLoop(allModules, context.get(EngineProperties.class).get());
		scenes.run();
	}
	
}
