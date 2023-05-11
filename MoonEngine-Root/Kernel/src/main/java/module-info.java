
open module engine.moon.kernel {
	
	requires transitive commons.action;
	requires transitive spring.context;
	requires transitive spring.beans;
	requires transitive spring.boot;
	requires transitive spring.core;
	requires transitive spring.boot.autoconfigure;
	
	exports com.greentree.engine.moon.kernel;
	exports com.greentree.engine.moon.kernel.annotation;
	
}
