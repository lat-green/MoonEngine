package com.greentree.engine.moon.editor;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import com.greentree.engine.moon.modules.Engine;

public class Main {
	
	public static void main(String[] args) {
		System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
		System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
		
		Engine.launch(args, new InitSceneModule(), new InitAssetModule(), new ExitOnESCAPE());
	}
	
}
