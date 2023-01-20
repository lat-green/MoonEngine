open module engine.moon.ecs {
	
	requires transitive commons.action;
	requires transitive commons.graph;
	
	exports com.greentree.common.ecs;
	exports com.greentree.common.ecs.pool;
	exports com.greentree.common.ecs.filter;
	exports com.greentree.common.ecs.system;
	exports com.greentree.common.ecs.system.debug;
	exports com.greentree.common.ecs.component;
	exports com.greentree.common.ecs.annotation;
	
}
