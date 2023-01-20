package com.greentree.engine.moon;

import java.util.ArrayList;
import java.util.Collections;

import com.greentree.engine.moon.module.EngineModule;
import com.greentree.engine.moon.module.base.EngineLoop;

public final class Engine {
	
	private Engine() {
	}
	
	public static void launch(String[] args, EngineModule... modules) {
		final var list = new ArrayList<EngineModule>();
		Collections.addAll(list, modules);
		final var scenes = new EngineLoop(list);
		scenes.run();
	}
	
}
