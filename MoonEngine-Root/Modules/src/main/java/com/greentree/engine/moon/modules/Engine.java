package com.greentree.engine.moon.modules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.greentree.commons.util.classes.info.TypeUtil;
import com.greentree.engine.moon.modules.phase.AnnotatedCWRDMethodModuleInfo;
import com.greentree.engine.moon.modules.phase.EnginePhase;
import com.greentree.engine.moon.modules.phase.FieldAnalizeCWRDMethodModuleInfo;
import com.greentree.engine.moon.modules.phase.LaunchEnginePhase;
import com.greentree.engine.moon.modules.phase.MergeCWRDMethodModuleInfo;
import com.greentree.engine.moon.modules.phase.MethodModuleSorter;
import com.greentree.engine.moon.modules.phase.OnCWRDMethodModuleSorter;
import com.greentree.engine.moon.modules.phase.TerminateEnginePhase;
import com.greentree.engine.moon.modules.phase.UpdateEnginePhase;
import com.greentree.engine.moon.modules.scanner.CollectionModuleDefenitionScanner;
import com.greentree.engine.moon.modules.scanner.ConfigModuleContainerScanner;
import com.greentree.engine.moon.modules.scanner.ModuleDefenition;
import com.greentree.engine.moon.modules.scanner.ServiceLoaderModuleDefenitionScanner;

public final class Engine {
	private Engine() {
	}
	
	public static void launch(String[] args, EngineModule... modules) {
		var scanner = new ConfigModuleContainerScanner()
				.addScanner(new ServiceLoaderModuleDefenitionScanner())
				.addScanner(new CollectionModuleDefenitionScanner(modules));
		
		var scanModules = scanner.scan().map(ModuleDefenition::build).toList();
		
		var properties = new EnginePropertiesBase();
		
		properties.add(new ExitManagerProperty());
		
		runPhases(scanModules, new LaunchEnginePhase(properties), new UpdateEnginePhase(properties),
				new TerminateEnginePhase());
	}
	
	@SuppressWarnings("unchecked")
	private static <T> List<T> filter(Collection<?> eleemnts, Class<T> cls) {
		return new ArrayList<>((List<T>) eleemnts.stream().filter(x -> cls.isInstance(x)).toList());
	}
	
	private static <T extends EngineModule> void runPhase(MethodModuleSorter sorter, EnginePhase<T> phase,
			Collection<? extends EngineModule> allModules) {
		@SuppressWarnings("rawtypes")
		var moduleClass = TypeUtil.<T, EnginePhase>getFirstAtgument(phase.getClass(), EnginePhase.class).toClass();
		var filteredModules = filter(allModules, moduleClass);
		sorter.sort(filteredModules, moduleClass.getMethods()[0].getName());
		phase.run(filteredModules);
	}
	
	private static void runPhases(Collection<? extends EngineModule> modules,
			EnginePhase<?>... phases) {
		var sorter = new OnCWRDMethodModuleSorter(new MergeCWRDMethodModuleInfo(new AnnotatedCWRDMethodModuleInfo(),
				new FieldAnalizeCWRDMethodModuleInfo()));
		for(var phase : phases) {
			runPhase(sorter, phase, modules);
		}
	}
	
}
