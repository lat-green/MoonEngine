open module engine.moon.ecs {
	
	requires transitive commons.action;
	requires transitive commons.graph;
	requires transitive commons.injector;
	
	exports com.greentree.engine.moon.ecs;
	exports com.greentree.engine.moon.ecs.pool;
	exports com.greentree.engine.moon.ecs.filter;
	exports com.greentree.engine.moon.ecs.system;
	exports com.greentree.engine.moon.ecs.system.debug;
	exports com.greentree.engine.moon.ecs.component;
	exports com.greentree.engine.moon.ecs.annotation;
	
}
