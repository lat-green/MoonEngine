package com.greentree.engine.moon;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ServiceLoader;

import com.greentree.engine.moon.module.EngineModule;
import com.greentree.engine.moon.module.base.EngineLoop;

public final class Engine {
	
	private Engine() {
	}
	
	public static void launch(String[] args, EngineModule... modules) {
		System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
		System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
		
		final var list = new ArrayList<EngineModule>();
		Collections.addAll(list, modules);
		
		final var autoAddModules = ServiceLoader.load(EngineModule.class);
		for(var m : autoAddModules)
			list.add(m);
		
		final var scenes = new EngineLoop(list);
		scenes.run();
	}
	
}
