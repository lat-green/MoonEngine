package com.greentree.engine.moon.modules;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ServiceLoader;

import com.greentree.engine.moon.modules.base.EngineLoop;

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
		
		var allModules = new ArrayList<EngineModule>();
		
		for(var m : modules)
			allModules.add(m);
		for(var m : ServiceLoader.load(EngineModule.class))
			allModules.add(m);
		
		final var scenes = new EngineLoop(allModules);
		scenes.run();
	}
	
}
