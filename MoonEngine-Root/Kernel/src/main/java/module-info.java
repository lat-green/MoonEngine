import com.greentree.engine.moon.module.EngineModule;

open module engine.moon.kernel {
	
	requires transitive commons.graph;
	requires transitive commons.injector;
	
	exports com.greentree.engine.moon;
	exports com.greentree.engine.moon.module;
	exports com.greentree.engine.moon.module.annotation;
	
	uses EngineModule;
	
	
	
}
