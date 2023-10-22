package com.greentree.engine.moon.script;

import java.io.PrintStream;

public final class Console {
	
	private final PrintStream out;
	
	public Console(PrintStream out) {
		this.out = out;
	}
	
	public void log(Object obj) {
		out.println(obj);
	}
	
}