package com.greentree.engine.moon;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

import com.greentree.engine.moon.module.EngineModule;
import com.greentree.engine.moon.module.base.EngineLoop;

public final class Engine {
	
	private Engine() {
	}
	
	public static void launch(String[] args, EngineModule... modules) {
		try {
			System.setOut(new PrintStream(System.out, true, "utf-8"));
			System.setErr(new PrintStream(System.err, true, "utf-8"));
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		
		final var list = new ArrayList<EngineModule>();
		Collections.addAll(list, modules);
		final var scenes = new EngineLoop(list);
		scenes.run();
	}
	
}
