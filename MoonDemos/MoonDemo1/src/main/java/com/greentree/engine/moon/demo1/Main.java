package com.greentree.engine.moon.demo1;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.ComponentScan;

import com.greentree.engine.moon.modules.Engine;

@ComponentScan
public class Main {
	
	public static void main(String[] args) {
		System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
		System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
		
		Engine.launch(args, new InitSceneModule(), new InitAssetModule());
	}
	
}
