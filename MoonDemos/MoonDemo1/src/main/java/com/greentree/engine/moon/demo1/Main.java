package com.greentree.engine.moon.demo1;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.moon.base.modules.info.AnnotatedCWRDMethodModuleInfo;
import com.greentree.engine.moon.base.modules.info.FieldAnalizeCWRDMethodModuleInfo;
import com.greentree.engine.moon.base.modules.info.MergeCWRDMethodModuleInfo;
import com.greentree.engine.moon.base.modules.scanner.ConfigModuleContainerScanner;
import com.greentree.engine.moon.base.modules.scanner.ModuleDefenition;
import com.greentree.engine.moon.base.modules.scanner.ServiceLoaderModuleDefenitionScanner;
import com.greentree.engine.moon.base.modules.sorter.OnCWRDMethodModuleSorter;
import com.greentree.engine.moon.modules.Engine;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.modules.UpdateModule;

public class Main {
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void main(String[] args) {
		System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
		System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
		
		var scanner = new ConfigModuleContainerScanner()
				.addScanner(new ServiceLoaderModuleDefenitionScanner())
				.addModule(new InitSceneModule()).addModule(new InitAssetModule()).addModule(new ExitOnESCAPE());
		
		var scanModules = scanner.scan().map(ModuleDefenition::build).toList();
		
		var sorter = new OnCWRDMethodModuleSorter(new MergeCWRDMethodModuleInfo(new FieldAnalizeCWRDMethodModuleInfo(),
				new AnnotatedCWRDMethodModuleInfo()));

		var launchModules = new ArrayList<LaunchModule>(
				(List) scanModules.stream().filter(x -> x instanceof LaunchModule).toList());
		var updateModules = new ArrayList<UpdateModule>(
				(List) scanModules.stream().filter(x -> x instanceof UpdateModule).toList());
		var terminateModules = new ArrayList<TerminateModule>(
				(List) scanModules.stream().filter(x -> x instanceof TerminateModule).toList());
		
		sorter.sort(launchModules, "launch");
		sorter.sort(updateModules, "update");
		sorter.sort(terminateModules, "terminate");
		
		Engine.launch(args, p -> {
			for(var module : launchModules)
				module.launch(p);
		}, () -> {
			for(var module : updateModules)
				module.update();
		}, () -> {
			for(var module : terminateModules)
				module.terminate();
		});
	}
	
}
